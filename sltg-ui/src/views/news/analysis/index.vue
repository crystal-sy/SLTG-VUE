<template>
  <div class="app-container">
    <el-row>
      <el-col :span="6" class="card-box">
        <el-card>
          <div slot="header"><span>新闻总数</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="newsTotalNum" class="news_show" >{{ newsTotalNum }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6" class="card-box">
        <el-card>
          <div slot="header"><span>真实新闻数</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="newsRealNum" class="news_show" >{{ newsRealNum }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6" class="card-box">
        <el-card>
          <div slot="header"><span>虚假新闻数</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="newsFakeNum" class="news_show" >{{ newsFakeNum }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6" class="card-box">
        <el-card>
          <div slot="header"><span>用户新闻数</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="newsUserNum" class="news_show" >{{ newsUserNum }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24" class="card-box">
        <el-card>
          <div slot="header"><span>新闻趋势图</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="newsTrend" style="height: 420px" />
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24" class="card-box">
        <el-card>
          <div slot="header"><span>虚假新闻趋势图</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="fakeNewsTrend" style="height: 420px" />
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>真实新闻词云</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <img :src="studentImg"  style="height: 420px">
          </div>
        </el-card>
      </el-col>
      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>虚假新闻词云</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <img :src="studentImg"  style="height: 420px">
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import { getNewsTotalInfo } from "@/api/news/analysis";
  import echarts from "echarts";

  export default {
    name: "analysis",
    data() {
      return {
        // 新闻总数
        newsTotalNum: 0,
        // 真实新闻数
        newsRealNum: 0,
        // 虚假新闻数
        newsFakeNum: 0,
        // 用户新闻数
        newsUserNum: 0,
        // 新闻趋势信息
        newsTrend: null,
        // 虚假新闻趋势
        fakeNewsTrend: null,
        newsTrendDate: [],
        newsTrendNews: [],
        newsTrendFakeNews: [],
        // 词云
        studentImg: require('@/assets/img.jpg')
      };
    },
    mounted(){
      this.getNewsTotalInfo();
    },
    methods: {
      getNewsTotalInfo() {
        getNewsTotalInfo().then(response => {
            var result = response.data;
            this.newsTotalNum = result.newsTotalNum;
            this.newsRealNum = result.newsRealNum;
            this.newsFakeNum = result.newsFakeNum;
            this.newsUserNum = result.newsUserNum;
            this.newsTrendDate = result.newsTrendDate;
            this.newsTrendNews = result.newsTrendNews;
            this.newsTrendFakeNews = result.newsTrendFakeNews;
            this.initChart();
          }
        );
      },

      /** 趋势信息 */
      initChart() {
        this.newsTrend = echarts.init(this.$refs.newsTrend, "newsTrend");
        this.newsTrend.setOption({
          //x轴
          xAxis:{
            data: this.newsTrendDate
          },
          //y轴没有显式设置，根据值自动生成y轴
          yAxis:{},
          //数据-data是最终要显示的数据
          series:[{
            name: '新闻数',
            type: 'line',
            symbolSize: 8, //折线圆点的大小
            data: this.newsTrendNews,
            itemStyle: {
              normal: {
                label: { show: true } //是否在折线点上显示数字
              }
            }
          }]
        });

        this.fakeNewsTrend = echarts.init(this.$refs.fakeNewsTrend, "fakeNewsTrend");
        this.fakeNewsTrend.setOption({
          //x轴
          xAxis:{
            data: this.newsTrendDate
          },
          //y轴没有显式设置，根据值自动生成y轴
          yAxis:{},
          //数据-data是最终要显示的数据
          series:[{
            name: '虚假新闻数',
            type: 'line',
            symbolSize: 8, //折线圆点的大小
            data: this.newsTrendFakeNews,
            itemStyle: {
              normal: {
                label: { show: true } //是否在折线点上显示数字
              }
            }
          }]
        });
      },
    },
  };
</script>
