<template>
    <el-calendar v-model="value" :first-day-of-week=7 class="customcalendar"></el-calendar>
</template>
<script>
export default {
    name: 'calendar',
    data () {
        return {
            value: ""
        }
    },
    mounted () {
        document.querySelectorAll('.customcalendar .el-button-group button').forEach((item)=>{item.innerHTML = ''});
    },
}
</script>
<style lang="scss">
.customcalendar{
    .el-calendar__header{display:block;position: relative;padding:8px 10px;
        .el-calendar__title{text-align: center;}
        .el-calendar__button-group{position: absolute;top:0;left:5px;right:5px;bottom:0;
            .el-button-group{position: relative;width:100%;height:100%;
                button:nth-child(2){display: none;}
                .el-button{border:0;position: absolute;}
                .el-button:first-child{left:0;top:8px;background: url('~$img/left.png') center center;background-size:cover;width:16px;height:16px;padding:0;}
                .el-button:last-child{right:0;top:8px;background: url('~$img/right.png') center center;background-size:cover;width:16px;height:16px;padding:0;}
            }
        }
    }
    .el-calendar__body{padding:0 5px;     
        .el-calendar-table td .el-calendar-day:hover{color:#000;}
        .el-calendar-table td.is-selected .el-calendar-day,.el-calendar-table td.is-selected:hover .el-calendar-day{background:$base-color-default;color:#fff;border-radius:3px;}
        .el-calendar-table thead th{padding:7px 0;}
        .el-calendar-table tr td{border:0;}
        .el-calendar-table .el-calendar-day{height:25px;padding:0;text-align:center;line-height:25px;}
    }
}
</style>