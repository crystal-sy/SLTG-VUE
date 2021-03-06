package com.sltg.common.utils.poi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.sltg.common.utils.file.FileUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sltg.common.annotation.Excel;
import com.sltg.common.annotation.Excel.ColumnType;
import com.sltg.common.annotation.Excel.Type;
import com.sltg.common.annotation.Excels;
import com.sltg.common.core.domain.AjaxResult;
import com.sltg.common.core.text.Convert;
import com.sltg.common.exception.CustomException;
import com.sltg.common.utils.DateUtils;
import com.sltg.common.utils.DictUtils;
import com.sltg.common.utils.StringUtils;
import com.sltg.common.utils.file.FileTypeUtils;
import com.sltg.common.utils.file.ImageUtils;

/**
 * Excel????????????
 * 
 * @author styra~
 */
public class ExcelUtil<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * Excel sheet?????????????????????65536
     */
    public static final int sheetSize = 65536;

    /**
     * ???????????????
     */
    private String sheetName;

    /**
     * ???????????????EXPORT:???????????????IMPORT??????????????????
     */
    private Type type;

    /**
     * ???????????????
     */
    private Workbook wb;

    /**
     * ???????????????
     */
    private Sheet sheet;

    /**
     * ????????????
     */
    private Map<String, CellStyle> styles;

    /**
     * ????????????????????????
     */
    private List<T> list;

    /**
     * ????????????
     */
    private List<Object[]> fields;

    /**
     * ????????????
     */
    private short maxHeight;

    /**
     * ????????????
     */
    private final Map<Integer, Double> statistics = new HashMap<>();

    /**
     * ????????????
     */
    private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("######0.00");

    /**
     * ????????????
     */
    public Class<T> clazz;

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void init(List<T> list, String sheetName, Type type) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        createExcelField();
        createWorkbook();
    }

    /**
     * ???list???????????????????????????????????????excel??????
     *
     * @param list      ??????????????????
     * @param sheetName ??????????????????
     * @return ??????
     */
    public AjaxResult exportExcel(List<T> list, String sheetName) {
        this.init(list, sheetName, Type.EXPORT);
        return exportExcel();
    }

    /**
     * ???list???????????????????????????????????????excel??????
     *
     * @return ??????
     */
    public AjaxResult exportExcel() {
        OutputStream out = null;
        try {
            // ????????????????????????sheet.
            double sheetNo = Math.ceil(list.size() / sheetSize);
            for (int index = 0; index <= sheetNo; index++) {
                createSheet(sheetNo, index);

                // ????????????
                Row row = sheet.createRow(0);
                int column = 0;
                // ?????????????????????????????????
                for (Object[] os : fields) {
                    Excel excel = (Excel) os[1];
                    this.createCell(excel, row, column++);
                }
                if (Type.EXPORT.equals(type)) {
                    fillExcelData(index);
                    addStatisticsRow();
                }
            }
            String filename = FileUtils.encodingFilename(sheetName + ".xlsx");
            out = new FileOutputStream(FileUtils.getAbsoluteFile(filename));
            wb.write(out);
            return AjaxResult.success(filename);
        } catch (Exception e) {
            LOGGER.error("??????Excel??????{}", e.getMessage());
            throw new CustomException("??????Excel????????????????????????????????????");
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * ??????excel??????
     *
     * @param index ??????
     */
    public void fillExcelData(int index) {
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize, list.size());
        for (int i = startNo; i < endNo; i++) {
            Row row = sheet.createRow(i + 1 - startNo);
            // ??????????????????.
            T vo = list.get(i);
            int column = 0;
            for (Object[] os : fields) {
                Field field = (Field) os[0];
                Excel excel = (Excel) os[1];
                // ????????????????????????????????????
                field.setAccessible(true);
                this.addCell(excel, row, vo, field, column++);
            }
        }
    }

    /**
     * ??????????????????
     *
     * @param wb ???????????????
     * @return ????????????
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        // ??????????????????,??????????????????excel???????????????
        Map<String, CellStyle> styles = new HashMap<>();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font totalFont = wb.createFont();
        totalFont.setFontName("Arial");
        totalFont.setFontHeightInPoints((short) 10);
        style.setFont(totalFont);
        styles.put("total", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.LEFT);
        styles.put("data1", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        styles.put("data2", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        styles.put("data3", style);

        return styles;
    }

    /**
     * ???????????????
     */
    public void createCell(Excel attr, Row row, int column) {
        // ?????????
        Cell cell = row.createCell(column);
        // ???????????????
        cell.setCellValue(attr.name());
        setDataValidation(attr, column);
        cell.setCellStyle(styles.get("header"));
    }

    /**
     * ?????????????????????
     *
     * @param value ????????????
     * @param attr  ????????????
     * @param cell  ???????????????
     */
    public void setCellVo(Object value, Excel attr, Cell cell) {
        if (ColumnType.STRING == attr.cellType()) {
            cell.setCellValue(StringUtils.isNull(value) ? attr.defaultValue() : value + attr.suffix());
        } else if (ColumnType.NUMERIC == attr.cellType()) {
            if (StringUtils.isNotNull(value)) {
                cell.setCellValue(StringUtils.contains(Convert.toStr(value), ".") ? Convert.toDouble(value) : Convert.toInt(value));
            }
        } else if (ColumnType.IMAGE == attr.cellType()) {
            ClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(), cell.getRow().getRowNum(), (short) (cell.getColumnIndex() + 1),
                    cell.getRow().getRowNum() + 1);
            String imagePath = Convert.toStr(value);
            if (StringUtils.isNotEmpty(imagePath)) {
                byte[] data = ImageUtils.getImage(imagePath);
                getDrawingPatriarch(cell.getSheet()).createPicture(anchor,
                        cell.getSheet().getWorkbook().addPicture(data, getImageType(data)));
            }
        }
    }

    /**
     * ????????????
     */
    public static Drawing<?> getDrawingPatriarch(Sheet sheet) {
        if (sheet.getDrawingPatriarch() == null) {
            sheet.createDrawingPatriarch();
        }
        return sheet.getDrawingPatriarch();
    }

    /**
     * ??????????????????,????????????????????????
     */
    public int getImageType(byte[] value) {
        String type = FileTypeUtils.getFileExtendName(value);
        if ("JPG".equalsIgnoreCase(type)) {
            return Workbook.PICTURE_TYPE_JPEG;
        } else if ("PNG".equalsIgnoreCase(type)) {
            return Workbook.PICTURE_TYPE_PNG;
        }
        return Workbook.PICTURE_TYPE_JPEG;
    }

    /**
     * ??????????????????
     */
    public void setDataValidation(Excel attr, int column) {
        if (attr.name().contains("??????")) {
            sheet.setColumnWidth(column, 6000);
        } else {
            // ????????????
            sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
        }
        // ???????????????????????????????????????????????????.
        if (StringUtils.isNotEmpty(attr.prompt())) {
            // ??????????????????2-101?????????.
            setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, column, column);
        }
        // ???????????????combo???????????????????????????????????????
        if (attr.combo().length > 0) {
            // ??????????????????2-101???????????????????????????.
            setXSSFValidation(sheet, attr.combo(), 1, 100, column, column);
        }
    }

    /**
     * ???????????????
     */
    public void addCell(Excel attr, Row row, T vo, Field field, int column) {
        Cell cell;
        try {
            // ????????????
            row.setHeight(maxHeight);
            // ??????Excel?????????????????????????????????,??????????????????????????????,???????????????????????????.
            if (attr.isExport()) {
                // ??????cell
                cell = row.createCell(column);
                int align = attr.align().value();
                cell.setCellStyle(styles.get("data" + (align >= 1 && align <= 3 ? align : "")));

                // ??????????????????????????????
                Object value = getTargetValue(vo, field, attr);
                String dateFormat = attr.dateFormat();
                String readConverterExp = attr.readConverterExp();
                String separator = attr.separator();
                String dictType = attr.dictType();
                if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(DateUtils.parseDateToStr(dateFormat, (Date) value));
                } else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(convertByExp(Convert.toStr(value), readConverterExp, separator));
                } else if (StringUtils.isNotEmpty(dictType) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(convertDictByExp(Convert.toStr(value), dictType, separator));
                } else if (value instanceof BigDecimal && -1 != attr.scale()) {
                    cell.setCellValue((((BigDecimal) value).setScale(attr.scale(), attr.roundingMode())).toString());
                } else {
                    // ???????????????
                    setCellVo(value, attr, cell);
                }
                addStatisticsData(column, Convert.toStr(value), attr);
            }
        } catch (Exception e) {
            LOGGER.error("??????Excel??????", e);
        }
    }

    /**
     * ?????? POI XSSFSheet ???????????????
     *
     * @param sheet         ??????
     * @param promptTitle   ????????????
     * @param promptContent ????????????
     * @param firstRow      ?????????
     * @param endRow        ?????????
     * @param firstCol      ?????????
     * @param endCol        ?????????
     */
    public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
                              int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        dataValidation.createPromptBox(promptTitle, promptContent);
        dataValidation.setShowPromptBox(true);
        sheet.addValidationData(dataValidation);
    }

    /**
     * ????????????????????????????????????????????????,???????????????.
     *
     * @param sheet    ????????????sheet.
     * @param textlist ????????????????????????
     * @param firstRow ?????????
     * @param endRow   ?????????
     * @param firstCol ?????????
     * @param endCol   ?????????
     */
    public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        // ????????????????????????
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
        // ????????????????????????????????????????????????,?????????????????????????????????????????????????????????????????????
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // ?????????????????????
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        // ??????Excel???????????????
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
    }

    /**
     * ??????????????? 0=???,1=???,2=??????
     *
     * @param propertyValue ?????????
     * @param converterExp  ????????????
     * @param separator     ?????????
     * @return ????????????
     */
    public static String convertByExp(String propertyValue, String converterExp, String separator) {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(",");
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny(separator, propertyValue)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[0].equals(value)) {
                        propertyString.append(itemArray[1]).append(separator);
                        break;
                    }
                }
            } else {
                if (itemArray[0].equals(propertyValue)) {
                    return itemArray[1];
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * ???????????????
     *
     * @param dictValue ?????????
     * @param dictType  ????????????
     * @param separator ?????????
     * @return ????????????
     */
    public static String convertDictByExp(String dictValue, String dictType, String separator) {
        return DictUtils.getDictLabel(dictType, dictValue, separator);
    }

    /**
     * ??????????????????
     */
    private void addStatisticsData(Integer index, String text, Excel entity) {
        if (entity != null && entity.isStatistics()) {
            Double temp = 0D;
            if (!statistics.containsKey(index)) {
                statistics.put(index, temp);
            }

            temp = Double.valueOf(text);
            statistics.put(index, statistics.get(index) + temp);
        }
    }

    /**
     * ???????????????
     */
    public void addStatisticsRow() {
        if (statistics.size() > 0) {
            Cell cell;
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            Set<Integer> keys = statistics.keySet();
            cell = row.createCell(0);
            cell.setCellStyle(styles.get("total"));
            cell.setCellValue("??????");

            for (Integer key : keys) {
                cell = row.createCell(key);
                cell.setCellStyle(styles.get("total"));
                cell.setCellValue(DOUBLE_FORMAT.format(statistics.get(key)));
            }
            statistics.clear();
        }
    }

    /**
     * ??????bean???????????????
     *
     * @param vo    ????????????
     * @param field ??????
     * @param excel ??????
     * @return ??????????????????
     * @throws Exception ??????
     */
    private Object getTargetValue(T vo, Field field, Excel excel) throws Exception {
        Object o = field.get(vo);
        if (StringUtils.isNotEmpty(excel.targetAttr())) {
            String target = excel.targetAttr();
            if (target.contains(".")) {
                String[] targets = target.split("[.]");
                for (String name : targets) {
                    o = getValue(o, name);
                }
            } else {
                o = getValue(o, target);
            }
        }
        return o;
    }

    /**
     * ??????????????????get???????????????????????????
     */
    private Object getValue(Object o, String name) throws Exception {
        if (StringUtils.isNotNull(o) && StringUtils.isNotEmpty(name)) {
            Class<?> clazz = o.getClass();
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            o = field.get(o);
        }
        return o;
    }

    /**
     * ????????????????????????
     */
    private void createExcelField() {
        this.fields = new ArrayList<>();
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        for (Field field : tempFields) {
            // ?????????
            if (field.isAnnotationPresent(Excel.class)) {
                putToField(field, field.getAnnotation(Excel.class));
            }

            // ?????????
            if (field.isAnnotationPresent(Excels.class)) {
                Excels attrs = field.getAnnotation(Excels.class);
                Excel[] excels = attrs.value();
                for (Excel excel : excels) {
                    putToField(field, excel);
                }
            }
        }
        this.fields = this.fields.stream().sorted(Comparator.comparing(objects -> ((Excel) objects[1]).sort()))
                .collect(Collectors.toList());
        this.maxHeight = getRowHeight();
    }

    /**
     * ??????????????????????????????
     */
    public short getRowHeight() {
        double maxHeight = 0;
        for (Object[] os : this.fields) {
            Excel excel = (Excel) os[1];
            maxHeight = Math.max(maxHeight, excel.height());
        }
        return (short) (maxHeight * 20);
    }

    /**
     * ?????????????????????
     */
    private void putToField(Field field, Excel attr) {
        if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
            this.fields.add(new Object[]{field, attr});
        }
    }

    /**
     * ?????????????????????
     */
    public void createWorkbook() {
        this.wb = new SXSSFWorkbook(500);
    }

    /**
     * ???????????????
     *
     * @param sheetNo sheet??????
     * @param index   ??????
     */
    public void createSheet(double sheetNo, int index) {
        this.sheet = wb.createSheet();
        this.styles = createStyles(wb);
        // ????????????????????????.
        if (sheetNo == 0) {
            wb.setSheetName(index, sheetName);
        } else {
            wb.setSheetName(index, sheetName + index);
        }
    }
}