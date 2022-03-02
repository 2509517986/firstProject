/*
 * @Author: YJY
 * @Date: 2022-02-09 13:15:53
 * @System: proManagement
 * @Company: LESSO
 * @LastEditTime: 2022-02-14 20:29:07
 * @Description: file content
 */
import Vue from 'vue'

export const set_machine = (params) =>{
    let arr = [
        {title:'状态',urlName:'10.png', isUrlName:'10-1.png',name:'state'},
        {title:'烟雾',urlName:'25.png', isUrlName:'25-1.png',name:'smoke'},
        {title:'积水',urlName:'water.png',name:'isPonding'},
        {title:'舱内温度',urlName:'powerTemp.png',name:'inTemperature'},
        {title:'供电类型',urlName:'7.png', isUrlName:'7-1.png',name:'cityElectricity'},

        // {title:'电池巢',urlName:'powerError.png',name:'slotbatteryId'},

        {title:'电池1',urlName:'power.png',name:'slotbattery1'},
        {title:'电池2',urlName:'power.png',name:'slotbattery2'},
        {title:'电池3',urlName:'power.png',name:'slotbattery3'},
        {title:'电池4',urlName:'power.png',name:'slotbattery4'},
        // {title:'故障',urlName:'s1.png',name:''},//故障
        // {title:'槽空',urlName:'powerEmpty.png',name:''},//槽空

        {title:'温度',urlName:'temp.png',name:'temperatureCelsius'},
        {title:'湿度',urlName:'humidity.png',name:'humidity'},
        {title:'风速',urlName:'16.png', isUrlName:'16-1.png',name:'windSpeed'},
        {title:'雨量',urlName:'17.png', isUrlName:'17-1.png',name:'rainfall'},
        {title:'气压',urlName:'14.png', isUrlName:'14-1.png',name:'airPressure'},
        {title:'风向',urlName:'15.png', isUrlName:'15-1.png',name:'windDirection'},
    ];
    let colHtml = '';
    for(let i = 0; i < arr.length; i++){
        // 如果四个电池巢是空的值，那么就跳过渲染
        if( arr[i].title.search('电池') != -1 && !params[arr[i].name]){
            continue
        }
        colHtml += `<el-col class="col-6" key="${i}" title="${arr[i].title}">
                <img src="/static/images/${ params[arr[i].name] && arr[i].isUrlName ? arr[i].isUrlName : arr[i].urlName}" class="img"> 
                <span >${params[arr[i].name] ? params[arr[i].name] : '暂无' }</span>
            </el-col> `
    }

    let btnImgHtml = '';
    for(let i = 0; i < params.airportImg.length; i++){
        if(i < 3){
            btnImgHtml += `<img src="${ params.airportImg[i]}" class="btnImg" id="${params.airportId}" name="${params.airportName}" imgindex="${i}" onclick="toimgDetail(this,'jichao')" >`
        }
    }

    return `<div class="dialog-set-machine">
        <el-row :gutter="10" class="set-machine-row" >
            <el-col class="col-12" >
                <img src="/static/images/airport.png" class="img"> 
                <span>编号:${params.airportCode}</span>
            </el-col> 
            <el-col class="col-12">
                <span title="${params.airportName}" class="title">名称：${params.airportName}</span>
            </el-col> 
        </el-row>
        <el-row :gutter="10" class="set-machine-row">
            ${colHtml}
        </el-row>
        <el-row :gutter="10" class="set-machine-row" style="border-bottom:1px solid #E4E7ED;padding:10px;">
            <img src="/static/images/play.png" class="playImg" params="${JSON.stringify(params)}"  title="WEIGAN" onclick="machineEvent('WEIGAN')"> 
            <img src="/static/images/play.png" class="playImg" params="${JSON.stringify(params)}" title="JITOU" onclick="machineEvent('JITOU')"> 
            <img src="/static/images/play.png" class="playImg" params="${JSON.stringify(params)}" title="JIWEI" onclick="machineEvent('JIWEI')"> 
        </el-row>
        <el-row :gutter="10" class="set-machine-row">
            ${btnImgHtml}
        </el-row>


       

    </div>`
    console.log(params)

    return Vue.extend({
        template: ` <span>111</span>`,
        data() {
            return {
               
            }
        },
        created() {
           comsole.log(params,'parmas')
        },
        mounted() {
           
        },
        methods: {
            
        }
    })
}
