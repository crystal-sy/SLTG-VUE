<template>
  <div class="navbar">
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>
    <div class="home-header-bar">
      <span class="homeTitle">新闻管理</span>
      <el-button size="medium" icon="el-icon-refresh" @click="refresh()" class="homeRefresh"/>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  computed: {
    ...mapGetters([
      'device'
    ]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      }
    }
  },
  methods: {
    refresh() {
      alert("aa")
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 45px;
  overflow: hidden;
  position: relative;
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
    margin-left: 1.7rem;

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
</style>
