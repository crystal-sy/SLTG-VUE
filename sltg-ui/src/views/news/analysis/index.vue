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
        newsTotalNum: 10256,
        // 真实新闻数
        newsRealNum: 956,
        // 虚假新闻数
        newsFakeNum: 51,
        // 用户新闻数
        newsUserNum: 201,
        // 新闻趋势信息
        newsTrend: null,
        // 词云
        studentImg: require('@/assets/img.jpg')
      };
    },
    mounted(){
      this.getList();
    },
    methods: {
      /** 查询趋势信息 */
      getList() {
        this.newsTrend = echarts.init(this.$refs.newsTrend, "newsTrend");
        this.newsTrend.setOption({
          //x轴
          xAxis:{
            data:["1-9","1-10","1-11","1-12","1-13","1-14","1-15","1-16","1-17","1-18","1-19","1-20","1-21","1-22","1-23",
              "1-24","1-25","1-26","1-27","1-28","1-29","1-30","1-31","2-1","2-2","2-3","2-4","2-5","2-6","2-7"]
          },
          //y轴没有显式设置，根据值自动生成y轴
          yAxis:{},
          //数据-data是最终要显示的数据
          series:[{
            name:'新闻数',
            type:'line',
            data:[61, 100, 65, 89, 63, 76, 64, 68, 69, 89, 65, 61, 58, 65, 84, 62, 68, 65, 69, 67, 68, 65, 84, 62, 68, 65, 69, 67, 68, 86]
          },{
            name:'虚假新闻数',
            type:'line',
            data:[10, 13, 15, 14, 22, 13, 5, 7, 9, 15, 20, 10, 13, 15, 14, 22, 13, 5, 7, 9, 15, 20, 15, 14, 22, 13, 5, 7, 9, 15]
          }]
        });
      },
    },
  };
</script>
