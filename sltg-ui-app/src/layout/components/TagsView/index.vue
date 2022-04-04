<template>
  <div id="tags-view-container" class="tags-view-container">
    <scroll-pane ref="scrollPane" class="tags-view-wrapper" @scroll="handleScroll">
      <span v-for="(item,index) in navbar" :key="index" class="tags-view-item">
        <router-link :to="{path:item.url,query:{type:item.type}}">{{item.text}}</router-link>
      </span>
    </scroll-pane>
    <div class="SearchNav">
      <el-input
        v-model="input"
        placeholder="请输入内容"
        @keyup.native.enter="search(input)"
        class="searchInput fl">
        <Icon type="ios-refresh-empty" size="30"></Icon>
      </el-input>
      <el-button size="medium" icon="el-icon-search" @click="search()" class="homeSearch"/>
    </div>
  </div>
</template>

<script>
import ScrollPane from './ScrollPane'

export default {
  components: { ScrollPane },
  data() {
    return {
      visible: false,
      top: 0,
      left: 0,
      selectedTag: {},
      affixTags: [],
      navbar: [{
        text: '新闻库',
        path: '/home',
        type: 'home'
      },
        {
          text: '知识库',
          path: '/knowledge',
          type: 'knowledge'
        },
        {
          text: '用户新闻',
          path: '/news',
          type: 'news'
        },
      ],
    }
  },
  computed: {
    visitedViews() {
      return this.navbar
    },
    routes() {
      return this.$store.state.permission.routes
    },
  },
  watch: {
    visible(value) {
      if (value) {
        document.body.addEventListener('click', this.closeMenu)
      } else {
        document.body.removeEventListener('click', this.closeMenu)
      }
    }
  },
  methods: {
    isActive(route) {
      return route.path === this.$route.path
    },
    activeStyle(tag) {
      if (!this.isActive(tag)) return {};
      return {
        "color": "#1890ff",
        "font-size": "17px",
        "font-weight": "bold",
      };
    },
    search() {
      alert("aa")
    },
    initTags() {
      const affixTags = this.affixTags = this.filterAffixTags(this.routes)
      for (const tag of affixTags) {
        // Must have tag name
        if (tag.name) {
          this.$store.dispatch('tagsView/addVisitedView', tag)
        }
      }
    },
    closeMenu() {
      this.visible = false
    },
    handleScroll() {
      this.closeMenu()
    }
  }
}
</script>

<style lang="scss" scoped>
.tags-view-container {
  height: 49px;
  width: 100%;
  background: #f4f5f6;
  border-bottom: 1px solid #d8dce5;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .12), 0 0 3px 0 rgba(0, 0, 0, .04);
  .tags-view-wrapper {
    .tags-view-item {
      display: inline-block;
      position: relative;
      cursor: pointer;
      height: 26px;
      line-height: 26px;
      padding: 0 8px;
      width: 3.4rem;
      font-size: 17px;
      margin-left: 5px;
      margin-top: 5px;
      &:first-of-type {
        margin-left: 10px;
      }
      &:last-of-type {
        margin-right: 15px;
      }
    }
  }
  .contextmenu {
    margin: 0;
    background: #fff;
    z-index: 3000;
    position: absolute;
    list-style-type: none;
    padding: 5px 0;
    border-radius: 4px;
    font-size: 12px;
    font-weight: 400;
    color: #333;
    box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, .3);
    li {
      margin: 0;
      padding: 7px 16px;
      cursor: pointer;
      &:hover {
        background: #eee;
      }
    }
  }

  .SearchNav {
    width: 100%;
    overflow: hidden;
    overflow-x: auto;
    text-align: center;
    position: fixed;
    left: 0;
    font-size: 0;
    top: 2.2rem;
    background: #f4f5f6;
    font-family: '微软雅黑',serif;
    white-space: nowrap;
    z-index: 999;
    .tags-view-item {
      display: inline-block;
      height: 1rem;
      line-height: 1rem;
      width: 1.4rem;
      font-size: 16px;
      a {
        color: #000;
      }
      .router-link-active {
        color: #1890ff;
        font-size: 17px;
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
    }
  }
}
</style>

<style lang="scss">
.tags-view-wrapper {
  .tags-view-item {
    .el-icon-close {
      width: 16px;
      height: 16px;
      vertical-align: 2px;
      border-radius: 50%;
      text-align: center;
      transition: all .3s cubic-bezier(.645, .045, .355, 1);
      transform-origin: 100% 50%;
      &:before {
        transform: scale(.6);
        display: inline-block;
        vertical-align: -3px;
      }
      &:hover {
        background-color: #b4bccc;
        color: #fff;
      }
    }
  }
}
</style>
