<template>
  <div class="search-page home">
    <div v-loading="loading" class="contentNav">
      <div v-for="(item, index) in newsList" class="newsContent">
        <p style="font-size: 16px; font-weight: bold; color: #000; margin-right: 3.4rem;">{{item.newsTitle}}</p>
        <div>
          <div style=" font-size: 14px; margin-top: 0.6rem; color: #000;">
            <span style="margin-right: 0.2rem;">{{item.newsFrom}}</span> &nbsp;&nbsp;
            <span style=" margin-top: 0.6rem;">0&nbsp;评论</span>
            <el-button
              plain
              icon="el-icon-delete"
              size="mini"
              @click="handleDelete"
              style="float: right; font-size: 12px;"
            >删除</el-button>
          </div>
        </div>
        <div class="bottom_line"/>
      </div>
    </div>
    <div class="pulldownload" v-show="downLoadMore" @click="pulldownloadmore()">
      点击加载更多
    </div>
    <div class="nav">
      <div class="navItems" @click="go_home()">
        <div class="bottom_nav_icon icon_1"></div>
        <div class="bottom_nav_text">首页</div>
      </div>
      <div class="navItems">
        <div class="bottom_nav_icon icon_2"></div>
        <div class="bottom_nav_text_on">收藏</div>
      </div>
      <div class="navItems" @click="go_my()">
        <div class="bottom_nav_icon icon_3"></div>
        <div class="bottom_nav_text">我的</div>
      </div>
    </div>
  </div>
</template>

<script>
  import { knowledgeList, getKnowledgeInfo } from "@/api/news/knowledge";

  export default {
    name: 'home',
    data() {
      return {
        // 遮罩层
        loading: true,
        // 加载
        downLoadMore: true,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          newsTitle: undefined,
        },
        // 新闻表格数据
        newsList: null,
      }
    },
    created() {
      this.getList();
    },
    methods: {
      go_home() {
        this.$router.push("/home");
      },
      go_my() {
        this.$router.push("/system/user");
      },
      /** 查询新闻列表 */
      getList() {
        this.loading = true;
        knowledgeList(this.queryParams).then(response => {
            this.newsList = response.rows;
            this.total = response.total;
            this.loading = false;
          }
        );
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const userIds = row.userId || this.ids;
        this.$confirm('是否确认删除用户编号为"' + userIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delUser(userIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
      },
      pulldownloadmore() {
        this.queryParams.pageNum = this.queryParams.pageNum + 1;
        this.loading = true;
        knowledgeList(this.queryParams).then(response => {
            this.newsList.push(response.rows);
            this.loading = false;
          }
        );
      },
    }
  }
</script>

<style scoped lang="scss">
  .search-page {
    // background-image: url("../assets/images/login-background.jpg");
    background-size: cover;
  }

  .home {
    .search-icon {
      cursor: pointer;
      font-size: 25px;
      position: absolute;
      vertical-align: middle;
      margin: 6px;
      text-align: center;
    }

    .title {
      margin: 0 auto 30px auto;
      text-align: center;
      font-size: 30px;
      color: #606266;
    }

    .contentNav {
      margin-left: 0.5rem;
      margin-right: 0.5rem;
      .newsContent {
        width: 100%;
        .newsDetail {
          width: 94%;
          display: block;
          position: relative;
          margin: 0 auto;
          padding-bottom: 0.15rem;
        }
        .bottom_line {
          margin-top: 20px;
          border-top: 1px solid #1890ff;
          @media screen and (-webkit-min-device-pixel-ratio: 3) {
            transform: scaleY(0.33333);
          }
          @media screen and (-webkit-min-device-pixel-ratio: 2) {
            transform: scaleY(0.5);
          }
        }
      }
    }

    .pulldownload {
      margin-bottom: 1.3rem;
      width: 100%;
      height: 1.5rem;
      line-height: 1.5rem;
      color: #000;
      font-size: 18px;
      text-align: center;
    }
  }

  .nav {
    width: 100%;
    position: fixed;
    left: 0;
    bottom: 0;
    text-align: center;
    font-size: 10px;
    overflow: hidden;
    z-index: 1000;
    opacity: 1;
    background: #fff;
    box-shadow: 0 0 5px #ccc;
    height: 1.2rem;
    .navItems {
      display: block;
      float: left;
      width: 33%;
      height: 100%;
      color: #999;
      padding-top: 5px;
      .bottom_nav_icon{
        height: 57%;
        margin: 0 auto;
      }
      .icon_1{
        background: url(/bottom_nav/home_dark_icon.png) no-repeat center center;
        background-size: contain;
      }
      .icon_2{
        background: url(/bottom_nav/collect_dark_icon.png) no-repeat center center;
        background-size: contain;
      }
      .icon_3{
        background: url(/bottom_nav/head_dark_icon.png) no-repeat center center;
        background-size: contain;
      }
      .bottom_nav_text{
        height: 43%;
      }
      .bottom_nav_text_on{
        height: 43%;
        color: #1890ff;
      }
    }
  }
</style>
