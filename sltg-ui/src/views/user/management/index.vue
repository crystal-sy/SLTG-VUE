<template>
  <div class="app-container">
    <!--新闻数据-->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item label="新闻标题" prop="newsTitle">
        <el-input
          v-model="queryParams.newsTitle"
          placeholder="请输入新闻标题"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="检测类型" prop="detectionType">
        <el-select
          v-model="queryParams.detectionType"
          placeholder="检测类型"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in detectionTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['user:news:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['user:news:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['user:news:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :disabled="multiple"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['user:news:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="newsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="新闻编号" align="center" key="newsId" prop="newsId" v-if="columns[0].visible" />
      <el-table-column label="新闻标题" align="center" key="newsTitle" prop="newsTitle" v-if="columns[1].visible" :show-overflow-tooltip="true" width="400" />
      <el-table-column label="新闻日期" align="center" key="newsDate" prop="newsDate" v-if="columns[2].visible" />
      <el-table-column label="虚假检测百分比" align="center" key="detectionPercent" prop="detectionPercent" v-if="columns[3].visible" />
      <el-table-column label="检测类型" align="center" key="detectionType" v-if="columns[4].visible" >
        <template slot-scope="scope">
          <dict-tag :options="detectionTypeOptions" :value="scope.row.detectionType"/>
        </template>
      </el-table-column>
      <el-table-column label="新闻来源" align="center" key="newsFrom" prop=newsFrom v-if="columns[5].visible" />
      <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[6].visible" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        width="160"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row.newsId)"
            v-hasPermi="['user:news:query']"
          >详情</el-button>
          <el-button v-if="scope.row.detectionType !== '4'"
             size="mini"
             type="text"
             icon="el-icon-search"
             @click="handleRelation(scope.row)"
             v-hasPermi="['user:news:query']"
          >相关新闻</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改新闻内容对话框 -->
    <el-dialog v-loading="loading" :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="新闻标题" prop="newsTitle">
              <el-input v-model="form.newsTitle" type="textarea" placeholder="请输入新闻标题" maxlength="100"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="新闻日期" prop="newsDate">
              <el-date-picker
                v-model="form.newsDate"
                size="small"
                style="width: 140px"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="新闻日期"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item label="新闻来源" prop="newsFrom">
              <el-input v-model="form.newsFrom" placeholder="请输入新闻来源" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="内容文件" prop="contentFile">
              <el-upload
                ref="contentUpload"
                accept=".txt"
                v-model="form.contentFile"
                :headers="upload.headers"
                :action="upload.url"
                :disabled="upload.isUploading"
                :on-progress="handleFileUploadProgress"
                :on-success="handleContentFileSuccess"
                :on-change="onContentExceed"
                :on-remove="removeContentFile"
                :auto-upload="true"
                :file-list="contentFileList"
                :on-preview="handlePreview"
              >点击上传新闻内容文件</el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="评论文件" prop="commentFile">
              <el-upload
                ref="commentUpload"
                accept=".txt"
                v-model="form.commentFile"
                :headers="upload.headers"
                :action="upload.url"
                :disabled="upload.isUploading"
                :on-progress="handleFileUploadProgress"
                :on-success="handleCommentFileSuccess"
                :on-change="onCommentExceed"
                :on-remove="removeCommentFile"
                :auto-upload="true"
                :file-list="commentFileList"
                :on-preview="handlePreview"
              >点击上传新闻评论文件</el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="">
              <div style="color:red">提示：仅允许导入“txt”格式文件！</div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 新闻内容详细 -->
    <el-dialog v-loading="loading" title="新闻内容详细" :visible.sync="openDetail" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px" size="mini">
        <el-row>
          <el-col :span="24">
            <el-form-item label="新闻标题：">{{ form.newsTitle }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="新闻日期：">{{ form.newsDate }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="新闻来源：">{{ form.newsFrom }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="新闻主题：">{{ form.newsTopic }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="检测百分比：">{{ form.detectionPercent }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="检测类型：">{{ form.detectionDesc }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" @click.native="handleDetailPreview(form.newsId + '/0')">
            <el-form-item label="内容文件：" style="color:#00ccff;text-decoration:underline">{{ form.contentFile }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" @click.native="handleDetailPreview(form.newsId + '/1')">
            <el-form-item label="评论文件：" style="color:#00ccff;text-decoration:underline">{{ form.commentFile }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="创建时间：">{{ form.createTime }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="更新时间：">{{ form.updateTime }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="openDetail = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { userNewsList, getUserNews, delUserNews, exportUserNews, addUserNews, updateUserNews, downloadUserNews } from "@/api/user/management";
  import { getToken } from "@/utils/auth";

  export default {
    name: "News",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 导出遮罩层
        exportLoading: false,
        // 是否显示弹出层
        open: false,
        openDetail: false,
        contentFileList:[],
        commentFileList:[],
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 新闻表格数据
        newsList: null,
        // 对话框标题
        title: "",
        // 日期范围
        dateRange: [],
        // 新闻检测类型数据字典
        detectionTypeOptions: [],
        // 表单参数
        form: {},
        defaultProps: {
          children: "children",
          label: "label"
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          newsTitle: undefined,
          detectionType: undefined
        },
        // 新闻文件上传参数
        upload: {
          // 是否禁用上传
          isUploading: false,
          // 设置上传的请求头部
          headers: { Authorization: "Bearer " + getToken() },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/user/management/importData"
        },
        // 列信息
        columns: [
          { key: 0, label: `新闻编号`, visible: true },
          { key: 1, label: `新闻标题`, visible: true },
          { key: 2, label: `新闻日期`, visible: true },
          { key: 3, label: `虚假检测百分比`, visible: true },
          { key: 4, label: `检测类型`, visible: true },
          { key: 5, label: `新闻来源`, visible: true },
          { key: 6, label: `创建时间`, visible: true }
        ],
        // 表单校验
        rules: {
          newsTitle: [
            { required: true, message: "新闻标题不能为空！", trigger: "blur" }
          ],
          newsDate: [
            { required: true, message: "新闻日期不能为空！", trigger: "blur" }
          ],
          newsFrom: [
            { required: true, message: "新闻来源不能为空！", trigger: "blur" }
          ],
          contentFile: [
            { required: true, message: "请上传新闻内容文件！", trigger: "blur" }
          ]
        }
      };
    },
    created() {
      this.getList();
      this.getDicts("sys_detection_type").then(response => {
        this.detectionTypeOptions = response.data;
      });
    },
    methods: {
      /** 查询用户新闻列表 */
      getList() {
        this.loading = true;
        userNewsList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
            this.newsList = response.rows;
            this.total = response.total;
            this.loading = false;
          }
        );
      },
      // 筛选节点
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      // 节点单击事件
      handleNodeClick(data) {
        this.getList();
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.dateRange = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.newsId);
        this.single = selection.length !== 1;
        this.multiple = !selection.length;
      },
      /** 搜索按钮操作 */
      handleDetail(newsId) {
        this.loading = true;
        this.reset();
        getUserNews(newsId).then(response => {
            this.form = response.data;
            this.openDetail = true;
            this.loading = false;
          }
        );
      },

      onContentExceed(file, contentFileList) {
        if (contentFileList.length > 1) {
          contentFileList.splice(0, 1);
        }
      },

      onCommentExceed(file, commentFileList) {
        if (commentFileList.length > 1) {
          commentFileList.splice(0, 1);
        }
      },

      removeContentFile() {
        this.form.contentFile = '';
      },

      removeCommentFile() {
        this.form.commentFile = '';
      },

      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "新增新闻";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const newsId = row.newsId || this.ids;
        getUserNews(newsId).then(response => {
          this.form = response.data;
          this.contentFileList.push({name:this.form.contentFile, url:this.form.newsId + '/0'});
          if (this.form.commentFile !== '') {
            this.commentFileList.push({name:this.form.commentFile, url:this.form.newsId + '/1'});
          }
          this.open = true;
          this.title = "修改新闻";
        });
      },

      handlePreview(file) {
        if (file.url === undefined) {
          this.msgError("该文件还未提交，不可下载，请本地查找！");
        } else {
          downloadUserNews(file.url).then(response => {
            this.download(response.msg);
          });
        }
      },

      handleDetailPreview(url) {
        downloadUserNews(url).then(response => {
          this.download(response.msg);
        });
      },

      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
        this.$refs.contentUpload.clearFiles();
        this.$refs.commentUpload.clearFiles();
      },

      // 表单重置
      reset() {
        this.form = {
          newsId: undefined,
          newsTitle: undefined,
          newsDate: undefined,
          newsFrom: undefined,
          contentFile: undefined,
          commentFile: undefined
        };
        this.contentFileList = [];
        this.commentFileList = [];
        this.resetForm("form");
      },

      /** 提交按钮 */
      submitForm: function() {
        this.loading = true;
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.newsId !== undefined) {
              updateUserNews(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
                this.$refs.contentUpload.clearFiles();
                this.$refs.commentUpload.clearFiles();
                this.loading = false;
              }).catch(() => {
                this.loading = false;
              });
            } else {
              addUserNews(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
                this.$refs.contentUpload.clearFiles();
                this.$refs.commentUpload.clearFiles();
                this.loading = false;
              }).catch(() => {
                this.loading = false;
              });
            }
          }
        });
      },

      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleContentFileSuccess(response, file, fileList) {
        this.upload.isUploading = false;
        if (response.code === 200) {
          this.form.contentFile = response.msg;
          this.$refs["form"].validate();
        } else {
          this.$refs.contentUpload.clearFiles();
          this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
        }
      },

      // 文件上传成功处理
      handleCommentFileSuccess(response, file, fileList) {
        this.upload.isUploading = false;
        if (response.code === 200) {
          this.form.commentFile = response.msg;
        } else {
          this.$refs.commentUpload.clearFiles();
          this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
        }
      },

      /** 删除按钮操作 */
      handleDelete(row) {
        const newsIds = row.newsId || this.ids;
        this.$confirm('是否确认删除新闻编号为"' + newsIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delUserNews(newsIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
      },

      /** 导出按钮操作 */
      handleExport(row) {
        const newsIds = row.newsId || this.ids;
        this.$confirm("是否确认导出所选的新闻数据项?", "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportUserNews(newsIds);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
      }
    }
  };
</script>
