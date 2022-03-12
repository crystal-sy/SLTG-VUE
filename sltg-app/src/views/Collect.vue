<template lang="html">
  <div id="collect">
  	<headerBar class="collect-header">
  		<div slot="collect">
  			<Icon type="ios-arrow-back" size="28" class="fl back" @click.native="$router.go(-1)"></Icon>
            <span class="title">收藏</span>
  		</div>
  	</headerBar>
    <div class="collect_content">
      <div class="con">
        <router-link v-for="(item,index) in myCollect" :key="index" class="collect_items clearfix" :to="{
                    name:'newsDetail',
                    params:
                        {   
                            id:item.tag_id,
                            title:item.title,
                            media_info:item.media_info,
                            media_name:item.media_name,
                            datetime:item.datetime,
                            abstract:item.abstract,
                            image_list:item.image_list,
                            repin_count:item.repin_count,
                            comment_count:item.comment_count,
                            keywords:item.keywords,
                            collected:item.collected,
                        }
                }" >
          <div v-if="!item.image_list">1</div>
          <img :src="imgs.url" alt="" v-for="(imgs,index) in item.image_list" v-show="index === 1" class="fr" v-else>
          <h2 class="collect_title">{{item.title}}</h2>
          <div class="name_comment">
            <span class="collect_name">{{item.media_name}}</span>
            <span class="collect_comment">{{item.comment_count}}&nbsp;评论</span>
            <Button class="collect_cancel" type="ghost" size="large" icon="trash-a">删除</Button>
          </div>
        </router-link>
      </div>
    </div>
    <bottom-nav></bottom-nav>
  </div>
</template>

<script>
import axios from 'axios'
import headerBar from '../components/Header-bar.vue'
import bottomNav from '../components/Bottom-nav.vue'
export default {
    components: {
        headerBar,
        bottomNav
    },
    data() {
        return {
            myCollect: []
        }
    },
    methods: {
        getMY() {
            axios.get('/src/static/my.json')
                .then(res => {
                    const Data = res.data;
                    this.myCollect = Data.data;
                })
        }
    },
    created() {
        this.getMY();
    }
}
</script>

<style lang="less">@import '../assets/css/border.less';
#collect {
    .collect-header {
        background: #1890ff;
        box-shadow: 0 1px #ccc;
        .title {
            color: #fff;
            font-size: 18px;
            vertical-align: middle;
        }
        .back {
            color: #fff;
            line-height: 1.2rem;
            margin-left: 0.3rem;
        }
    }
    .collect_content {
        margin-top: 1.3rem;
        margin-bottom: 1.8rem;
        .con {
            width: 94%;
            margin: 0 auto;
            position: relative;
            .collect_items {
                display: block;
                border-bottom: 1px solid #1890ff;
                padding: 0.3rem 0;
                .collect_title {
                    font-size: 14px;
                    font-weight: bold;
                    color: #000;
                    margin-right: 3.4rem;
                }
                img {
                    width: 3rem;
                }
                .name_comment {
                    margin-top: 0.6rem;
                    font-size: 14px;
                    color: #000;
                    .collect_name {
                        margin-right: 0.2rem;
                    }
                .collect_cancel {
                    float: right;
                    font-size: 12px;
                    }
                }
            }
        }
    }
}
</style>
