<template>
  <div class="search-page home">
    <div class="navbar">
      <div class="home-header-bar">
        <span class="homeTitle">新闻管理</span>
      </div>
    </div>
    <div class="SearchNav">
      <span class="tags-view-item" @click="go_home()">
        <el-button class="router-link-deactive">新闻库</el-button>
      </span>
      <span class="tags-view-item" @click="go_knowledge()">
        <el-button class="router-link-deactive">知识库</el-button>
      </span>
      <span class="tags-view-item">
        <el-button class="router-link-active">用户新闻</el-button>
      </span>
      <el-input
        v-model="queryParams.newsTitle"
        placeholder="请输入内容"
        @keyup.native.enter="search(queryParams.newsTitle)"
        class="searchInput fl">
        <Icon type="ios-refresh-empty" size="30"></Icon>
      </el-input>
      <el-button size="medium" icon="el-icon-search" @click="search()" class="homeSearch"/>
    </div>
    <div v-loading="loading" class="contentNav">
      <div v-for="(item, index) in newsList" class="newsContent" @click="go_detail(item.newsId)">
        <p style="font-size: 16px; font-weight: bold; color: #000;">{{item.newsTitle}}</p>
        <div>
          <div style=" font-size: 10px; margin-top: 0.15rem;">
            <span style="color: #000;">{{item.newsFrom}}</span> &nbsp;&nbsp;
            <span style="color: #000;">{{item.detectionPercent}}</span>
            <span style="float: right; color: #000;">{{item.newsDate}}</span>
          </div>
        </div>
        <div class="bottom_line"/>
      </div>
    </div>
    <div class="pulldownload" v-show="downLoadMore" @click="pulldownloadmore()">
      点击加载更多
    </div>
    <div class="nav">
      <div class="navItems" >
        <div class="bottom_nav_icon icon_1"></div>
        <div class="bottom_nav_text_on">首页</div>
      </div>
      <div class="navItems" @click="go_collect()">
        <div class="bottom_nav_icon icon_2"></div>
        <div class="bottom_nav_text">收藏</div>
      </div>
      <div class="navItems" @click="go_my()">
        <div class="bottom_nav_icon icon_3"></div>
        <div class="bottom_nav_text">我的</div>
      </div>
    </div>
  </div>
</template>

<script>
  import { userNewsList, getUserNews } from "@/api/user/management";

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
      search() {
        this.getList()
      },
      go_home() {
        this.$router.push("/home");
      },
      go_knowledge() {
        this.$router.push("/news/knowledge");
      },
      go_collect() {
        this.$router.push("/news/table");
      },
      go_detail(newId) {
        this.$router.push("/news/detail?type=user_news&new_id=" + newId);
      },
      go_my() {
        this.$router.push("/system/user");
      },
      pulldownloadmore() {
        this.queryParams.pageNum = this.queryParams.pageNum + 1;
        this.loading = true;
        userNewsList(this.queryParams).then(response => {
            this.newsList.push(response.rows);
            this.loading = false;
          }
        );
      },
      /** 查询新闻列表 */
      getList() {
        this.loading = true;
        userNewsList(this.queryParams).then(response => {
            this.newsList = response.rows;
            this.total = response.total;
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

.navbar {
  height: 45px;
  overflow: hidden;
  position: fixed;
  width: 100%;
  z-index: 999;
  background: #1890ff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .home-header-bar {
    text-align: center;
    z-index: 999;
    margin-top: 0.25rem;

    .homeTitle {
      color: #fff;
      font-size: 18px;
      vertical-align: middle;
      i {
        vertical-align: middle;
      }
    }
    .homeRefresh{
      display: inline-block;
      vertical-align: middle;
      float: right;
      background: #1890ff;
      border-color: #1890ff;
      margin-top: -0.25rem;
    }

    .el-button--medium {
      font-size: 25px;
      color: #fff;
    }
  }
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

  .SearchNav {
    width: 100%;
    overflow: hidden;
    overflow-x: auto;
    text-align: center;
    // position: fixed;
    left: 0;
    font-size: 0;
    padding-top: 45px;
    background: #f4f5f6;
    font-family: '微软雅黑',serif;
    white-space: nowrap;
    z-index: 999;
    .tags-view-item {
      display: inline-block;
      height: 1rem;
      line-height: 1rem;
      width: 3.3rem;
      padding-top: 5px;
      .router-link-deactive {
        color: #000;
        font-size: 17px;
      }
      .router-link-active {
        color: #1890ff;
        font-size: 18px;
        font-weight: bold;
      }
    }
    .searchInput{
      width: 90%;
      float: left;
    }

    .homeSearch{
      display: inline-block;
      vertical-align: middle;
      float: right;
      background: #f4f5f6;
      border-color: #f4f5f6;
    }

    .el-button--medium {
      font-size: 25px;
      padding: 0.1rem;
      background: #f4f5f6;
      border: 1px solid #f4f5f6;
    }
  }

  .contentNav {
    // margin-top: 85px;
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
        margin-top: 5px;
        border-top: 1px solid #ccc;
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
