<template>
  <div class="search-page home">
    <div class="navbar">
      <div class="home-header-bar">
        <el-button size="medium" icon="el-icon-arrow-left" class="itemLeft" @click="goBack()"/>
        <span class="homeTitle">新闻详情</span>
      </div>
    </div>
    <div v-loading="loading" class="contentNav">
      <h2 class="detail-title">{{newsInfo.newsTitle}}</h2>
      <div class="media_name">
        <img v-bind:src="options.img" height="360" width="360" alt="" class="avatar_url" />
        <span class="name">{{newsInfo.newsFrom}}</span><br>
        <span class="date">{{newsInfo.newsDate}}</span>
        <span class="newsType">真实</span>
      </div>
      <div class="contentNews">
        {{newsInfo.newsText}}
      </div>
      <hr>
      <div class="keywords">
        <span v-for="(item,index) in newsInfo.newsTheme.split(',')">
          {{item}}
        </span>
      </div>
    </div>
    <div class="nav">
      <el-input class="editComment" placeholder="写评论..." icon="edit"></el-input>
      <el-button size="medium" icon="el-icon-cloudy" class="itemCloud" @click="go_comment()"/>
      <el-button size="medium" icon="el-icon-star-off" class="itemRight_off" @click="add_collect()"/>
      <span class="commentNum" @click.native="tocomment">2805</span>
    </div>
  </div>
</template>

<script>
  import { getKnowledgeInfo } from "@/api/news/knowledge";
  import { getUserNews } from "@/api/user/management";
  import { getNews } from "@/api/news/list";
  import userAvatar from "./userAvatar";
  import userInfo from "./userInfo";
  import store from "@/store";

  export default {
    name: 'detail',
    components: { userAvatar, userInfo },
    data() {
      return {
        // 遮罩层
        loading: true,
        type: '',
        newsId: '',
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          newsTitle: undefined,
        },
        // 新闻表格数据
        newsInfo: null,
        options: {
          img: store.getters.avatar, //裁剪图片的地址
        },
      }
    },
    created() {
      this.type = this.$route.query.type;
      this.newsId = this.$route.query.new_id;
      this.getNewsInfo();
    },
    methods: {
      goBack(){
        this.$router.go(-1);
      },
      /** 查询新闻详情 */
      getNewsInfo() {
        this.loading = true;
        if (this.type === 'news') {
          getNews(this.newsId).then(response => {
              this.newsInfo = response.data;
              this.loading = false;
            }
          );
        } else if (this.type === 'knowledge') {
          getKnowledgeInfo(this.newsId).then(response => {
              this.newsInfo = response.data;
              this.loading = false;
            }
          );
        } else if (this.type === 'user_news') {
          getUserNews(this.newsId).then(response => {
              this.newsInfo = response.data;
              this.loading = false;
            }
          );
        } else {
          getKnowledgeInfo(this.newsId).then(response => {
              this.newsInfo = response.data;
              this.loading = false;
            }
          );
        }
      },
    }
  }
</script>

<style scoped lang="scss">
  .search-page {
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
      margin-top: 0.28rem;
      margin-right: 1rem;

      .itemLeft {
        margin-top: -0.28rem;
        display: inline-block;
        vertical-align: middle;
        float: left;
        border-color: #1890ff;
        background-color: #1890ff;
      }

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

    .contentNav {
      margin-left: 0.5rem;
      margin-right: 0.5rem;
      padding-top: 45px;
      .detail-title {
        color: #000;
        font-size: 20px;
        font-weight: bold;
      }
      .media_name {
        position: relative;
        margin-top: 0.5rem;
        .avatar_url {
          height: 1.1rem;
          width: 1.1rem;
          border-radius: 50%;
        }
        .name {
          position: absolute;
          font-size: 14px;
          font-weight: bold;
          left: 1.3rem;
          top: 0.1rem;
        }
        .date {
          position: absolute;
          left: 1.3rem;
          top: 0.6rem;
        }
        .newsType {
          position: absolute;
          font-weight: bold;
          font-size: 13px;
          left: 7.5rem;
          top: 0.1rem;
          background-color: #1890ff;
          color: #fff;
          text-align: center;
          border: 2px solid #ccc;
          padding: 0 0.2rem;
          border-radius: 1.4rem;
          margin-right: 0.3rem;
        }
      }
      .contentNews {
        margin-top: 0.5rem;
        font-size: 16px;
        color: #000;
        text-indent: 0.7rem;
        line-height: .8rem;
        margin-bottom: 0.2rem;
        img {
          text-align: center;
          display: block;
          width: 70%;
          margin: 0.2rem auto;
        }
      }
      .keywords {
        margin-top: 0.2rem;
        line-height: 0.8rem;
        span {
          margin-bottom: 0.3rem;
          text-align: center;
          margin-right: 0.3rem;
          display: inline-block;
          border: 1px solid #ccc;
          padding: 0 0.2rem;
          border-radius: 0.4rem;
        }
      }
      .zan{
        text-align: center;
        margin-top: .5rem;
        margin-bottom: .5rem;
        botton{
          padding-right: 1rem;
          color: #000;
        }
      }

      hr{
        height: 1px;
        background: #ccc;
      }
    }
  }

  .nav {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 1.3rem;
    z-index: 100;
    background: #fff;
    .editComment{
      display: inline-block;
      width: 68%;
      margin-left: .2rem;
      margin-top: .1rem;
    }
    .commentInco{
      margin-top: .2rem;
      margin-right: .55rem;
    }
    .showcomment{
      position: relative;
    }
    .itemCloud {
      display: inline-block;
      vertical-align: middle;
      border-color: #fff;
      font-size: 30px;
      padding: 10px;
    }
    .itemRight_on {
      display: inline-block;
      vertical-align: middle;
      border-color: #fff;
      font-size: 30px;
      padding: 10px;
      margin-left: 0;
      color: #1890ff;
    }
    .itemRight_off {
      display: inline-block;
      vertical-align: middle;
      border-color: #fff;
      font-size: 30px;
      padding: 10px;
      margin-left: 0;
    }
    .commentNum{
      position: absolute;
      margin-right: -1rem;
      padding: .05rem;
      border-radius: 80%;
      min-width: .55rem;
      text-align: center;
      line-height: .45rem;
      background: #1890ff;
      right: 28%;
      top: .06rem;
      color: #fff;
      font-size: 10px;
    }
  }
</style>


<style>
  .el-message-box {
    width: 360px;
  }
</style>
