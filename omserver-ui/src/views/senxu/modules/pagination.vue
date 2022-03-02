<template>
  <div :class="{'hidden':hidden}" class="pagination-container">
    <div class="autoplay" v-if="autoplay">
        <span>翻页间隔(秒)</span>
        <el-select v-model="value" placeholder="请选择" size="mini" @change="changetime">
            <el-option  v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
        <el-button size="mini" @click.native="autoplay_click" type="primary">{{play?'停止':'播放'}}</el-button>
    </div>
    <el-pagination v-if="pageSizeShow" size="mini" :background="background" :current-page.sync="currentPage" :page-size.sync="pageSize" :layout="layout" :page-sizes="pageSizes"  :total="total" v-bind="$attrs" @size-change="handleSizeChange" @current-change="handleCurrentChange" />    
    <div class="pageInput">
      <el-button v-if="firstend" size="mini" style="padding:9px 8px;" @click="firstClick">首页</el-button>
      <el-button class="btn-prev" icon="el-icon-arrow-left" size="mini" :disabled="currentPage==1?true:false" @click.native="prev"></el-button>
      <el-pagination class="custompagination" size="mini" :current-page.sync="currentPage" :page-size.sync="pageSize" layout="jumper" :page-sizes="pageSizes"  :total="total" v-bind="$attrs"  @current-change="handleCurrentChange"/>
      <span>/<font>{{pageTotal()}}</font></span>
      <el-button class="btn-next" icon="el-icon-arrow-right" size="mini" :disabled="currentPage==pageTotal()?true:false" @click.native="next"></el-button>
      <el-button v-if="firstend" size="mini" style="padding:9px 8px;" @click="endClick">尾页</el-button>
    </div>
  </div>
</template>

<script>
import { scrollTo } from '@/utils/scroll-to'

export default {
  name: 'CustomPagination',
  props: {
    autoplay: {
        type: Boolean,
        default: true
    },
    pageSizeShow: {
        type: Boolean,
        default: true
    },
    total: {
      required: true,
      type: Number
    },
    page: {
      type: Number,
      default: 1
    },
    limit: {
      type: Number,
      default: 20
    },
    pageSizes: {
      type: Array,
      default() {
        return [10, 20, 30, 50]
      }
    },
    layout: {
      type: String,
      default: 'total, sizes'
      // default: 'total, sizes, prev, pager, next, jumper'
    },
    background: {
      type: Boolean,
      default: true
    },
    autoScroll: {
      type: Boolean,
      default: true
    },
    hidden: {
      type: Boolean,
      default: false
    },
    firstend: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      thisPage: this.page,
      timer: "",
      play:false,
      value: 10000,
      options:[{label: "10",value: 10000},{label: "15",value: 15000},{label: "20",value: 20000},{label: "25",value: 25000},{label: "30",value: 30000}]
    }
  },
  computed: {
    currentPage: {
      // 当前页码
      get() {
        return this.page;
      },
      set(val) {
        this.thisPage = val;
        this.$emit('update:page', val)
      }
    },
    pageSize: {
      get() {
        return this.limit
      },
      set(val) {
        this.currentPage = 1;
        this.$emit('update:limit', val)
      }
    }
  },
  mounted () {
    let doms = document.getElementsByClassName('el-pagination__jump');
    for(let i = 0;i<doms.length;i++) {
      document.getElementsByClassName('el-pagination__jump')[i].childNodes[2].nodeValue = '';
      document.getElementsByClassName('el-pagination__jump')[i].childNodes[0].nodeValue = ''
    }
  },
  methods: {
    handleSizeChange(val) {
      let that =this;
      // 暂定播放
      that.play = false;
      clearTimeout(that.timer);
      // 下拉选择 条/页。 currentPage-当前页，val-一页多少条
      this.$nextTick(()=>{
        that.$emit('pagination', { page: 1, limit: val,time: that.value,pages:that.pageTotal() })
        that.$emit('autoplay',{ time: that.value,autoplay: that.play})
      });
      if (that.autoScroll) {
        scrollTo(0, 800)
      }
    },
    handleCurrentChange(val) {  
      // 选中页码。 val-当前页，pageSize-一页多少条
      let that =this;
      that.currentPage = that.thisPage
      
      that.$nextTick(()=>{
        that.$emit('pagination', { page: that.thisPage, limit: that.pageSize,time: that.value,pages:that.pageTotal() })
      });
      if (that.autoScroll) {
        scrollTo(0, 800)
      }
    },
    autoplay_click() {
      let that = this;
      if(that.currentPage >= that.pageTotal()) { that.msgSuccess("已是最后一页");return;}
      that.play = !that.play;
      if(that.play) {
        that.autoPlay();
      } else {
        that.noautoplay();
      }
      
      that.$nextTick(()=>{
        that.$emit('autoplay',{ time: that.value,autoplay: that.play})
      });
    },
    autoPlay() {
      let that =this;
      if((parseInt(that.thisPage)+1)>that.pageTotal()) {
        that.noautoplay();
        return;
      }
      that.timer = setTimeout(()=>{
        that.thisPage++;
        if(that.thisPage == that.pageTotal()) {that.play = false;that.$emit('autoplay',{ time: that.value,autoplay: that.play})}
          that.handleCurrentChange(that.thisPage);
          that.autoPlay();
      }, that.value);      
    },
    prev() {
      if(this.thisPage==1) {
        return;
      }
      this.noautoplay();
      this.thisPage--;
      this.currentPage = this.thisPage;
      this.handleCurrentChange(this.currentPage);
    },
    next() {
      if(this.thisPage >= this.pageTotal ()) {
        return;
      }
      this.noautoplay();
      this.thisPage++;
      this.currentPage = this.thisPage;
      this.handleCurrentChange(this.currentPage);
    },
    pageTotal () {
      let v = this.total % this.pageSize == 0? this.total / this.pageSize :Math.ceil(this.total / this.pageSize);
      return v;
    },
    noautoplay () {
        // 停止自动播放
        let that = this;
        that.play = false;
        clearTimeout(that.timer);
        return;
    },
    changetime() {     
      this.noautoplay();
      this.$nextTick(()=>{
        this.$emit('pagination', { page: this.currentPage, limit: this.pageSize,time: this.value,pages:this.pageTotal()})
        this.$emit('autoplay',{ time: this.value,autoplay: this.play})
      });
    },
    firstClick() {
      this.noautoplay();
      this.currentPage = 1;
      this.$nextTick(()=>{
       this.$emit('pagination', { page: 1, limit: this.pageSize,pages:this.pageTotal()})
      })
    },
    endClick() {
      this.noautoplay();
      this.currentPage = this.pageTotal();
      
      this.$nextTick(()=>{
        this.$emit('pagination', { page: this.currentPage, limit: this.pageSize,time: this.value,pages:this.pageTotal()})  
      })  
    }
  },
  destroyed() {
      clearTimeout(this.timer);
  }
}
</script>

<style lang="scss" scoped>
.pagination-container {
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 32px 16px;
  .autoplay {display:flex;align-items:center;margin-right:5px;
    .el-select{width: 80px;margin: 0 5px;}
    span{flex-shrink: 0;}
  }
  ::v-deep{.el-pagination__sizes{margin-right:0;}.el-input__inner::-webkit-outer-spin-button,.el-input__inner::-webkit-inner-spin-button {-webkit-appearance: none;}}
  .pageInput{display:flex;align-items:center;
    .btn-prev,.btn-next{padding:0;border:none;height:28px;text-align:center;line-height:28px;background-color: rgb(244, 244, 245);color: rgb(96, 98, 102);min-width: 30px;margin: 0px 5px;border-radius: 2px;}
    .btn-prev:disabled,.btn-next:disabled{color: #c0c4cc;}
    .el-input{width:60px;margin:0 5px;}
    span{font-size:13px;color:#606266;font{margin-left:3px;}}
  }
}
.custompagination{
  ::v-deep{
    .el-pagination__jump{margin-left:0;}
  }
}
.pagination-container.hidden {
  display: none;
}
</style>
