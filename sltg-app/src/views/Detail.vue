<template>
<div id="detail">
    <headerBar class="detail-header">
        <div slot="detail-header">
            新闻详情
            <Icon type="ios-arrow-back" size="28" class="fl back" @click.native="goBack"></Icon>
        </div>
    </headerBar>
    <div class="container">
        <h2 class="detail-title">{{$route.params.title}}</h2>
        <div class="media_name">
            <img src="../assets/imgs/profile.jpg" height="360" width="360" alt="" class="avatar_url">
            <span class="name">{{media_name}}</span><br>
            <span class="date">{{datetime}}</span>
            <span class="newsType">真实</span>
        </div>
        <div class="contentNews">
            {{abstract}}
        </div>
        <hr>
        <hr>
        <div class="keywords">
            <span v-for="(item,index) in keywords.split(',')">
				{{item}}
			</span>
        </div>
    </div>
    <comment :comment="comment_count"></comment>
</div>
</template>
<script>
import headerBar from '../components/Header-bar.vue'
import comment from '../components/Comment.vue'
import * as type from '../store/mutation-types.js'
import {
    mapActions,
    mapGetters
} from 'vuex'
export default {
    components: {
        headerBar,
        comment
    },
    data(){
        return {
            media_info:this.$route.params.media_info,
            title:this.$route.params.title
        }
    },
    methods: {
        goBack(){
            this.$store.commit(type.ROUTERCHANGE, !this.routerChange)
            this.$router.go(-1);
        }
    },
    computed: {
        ...mapGetters([
            'oneDetail',
            'Sharebox',
            'routerChange'
        ]),
        // avatar_url:function(){
        //     if(this.media_info.avatar_url){
        //         return this.media_info.avatar_url
        //     }else{
        //         return ''
        //     }
        // },
        media_name:function(){
            if(this.$route.params.media_name){
                return this.$route.params.media_name
            }else{
                return ''
            }
        },
        datetime:function(){
            if(this.$route.params.datetime){
                return this.$route.params.datetime
            }else{
                return ''
            }
        },
        abstract:function(){
            if(this.$route.params.abstract){
                return this.$route.params.abstract
            }else{
                return ''
            }
        },
        image_list:function(){
            if(this.$route.params.image_list){
                return this.$route.params.image_list
            }else{
                return ''
            }
        },
        keywords:function(){
            if(this.$route.params.keywords){
                return this.$route.params.keywords
            }else{
                return ''
            }
        },
        comment_count:function(){
            if(this.$route.params.comment_count){
                return this.$route.params.comment_count
            }else{
                return '8'
            }
        },
    },
    watch: {
        '$route': function() {
            this.$store.commit(type.SHOWSHAREBOX, false)
        }
    }
}
</script>
<style lang="less">@import '../assets/css/border.less';
#detail {
    hr{
        height: 1px;
        background: #ccc;
    }
    .detail-header {
        background: #1890ff;
        font-size: 18px;
        font-weight: bold;
        color: #fff;
        &::after {
            content: "";
            box-sizing: border-box;
            width: 100%;
            position: absolute;
            left: 0;
            bottom: 0;
            border-top: 1px solid #ccc;
            @media screen and (-webkit-min-device-pixel-ratio: 3) {
                transform: scaleY(0.33333);
            }
            @media screen and (-webkit-min-device-pixel-ratio: 2) {
                transform: scaleY(0.5);
            }
        }
        .back {
            line-height: 1.2rem;
            margin-left: 0.3rem;
        }
    }
    .container {
        margin: 1.6rem auto 1.5rem;
        width: 95%;
        .detail-title {
            color: #000;
            font-size: 20px;
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
    }
}
</style>
