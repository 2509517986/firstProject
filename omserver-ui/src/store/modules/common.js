import { getTreeLine, getTreeDept } from '@/api/senxu/devicemanager/line'
import { listData } from "@/api/system/dict/data";
const getTowerPoint = function (data){
    for(let i in data) {
        data[i]['key'] = data[i].type + '_'+data[i].id;
        if(data[i].children && data[i].children.length>0) {
            getTowerPoint(data[i].children);
        }
    }
}
 
const common = {
    state: {
        treeLine: {},
        treeDept: {},
        dicts: {} // 字典
    },

    mutations: {
        SET_TREE_LINE: (state, params) => {
            state.treeLine = params
        },
        SET_TREE_DEPT: (state, params) => {
            state.treeDept = params
        },
        SET_DICTS: (state, params) => {
          state.dicts = params;
        }
    },

    actions: {
        // 获取线路
        TreeLine({commit}, option) {
            return new Promise((resolve, reject) => {
                getTreeLine(option).then(res => {
                    if(res.code == 200) {
                        if(res && res.tree.length>0) {      
                            getTowerPoint(res.tree);                
                            commit('SET_TREE_LINE', res);
                        }                        
                    }
                    resolve();
                }).catch(error => {
                    reject(error)
                })
            })
        },
        // 获取机构
        TreeDept({commit}, option) {
            return new Promise((resolve, reject) => {
                getTreeDept(option).then(res => {     
                    getTowerPoint(res.tree); 
                    commit('SET_TREE_DEPT', res);
                    resolve()
                }).catch(error => {
                reject(error)
                })
            })
        },
        SetDicts({commit}, option) {
            return new Promise((resolve, reject) => {
                listData(option).then(response => {
                    if(response.code == 200 && response.rows && response.rows.length>0) {
                        let dics = {};
                        for(let i = 0; i<response.rows.length;i++) {
                        if(!dics[response.rows[i].dictType]) {
                            dics[response.rows[i].dictType] = {};
                            dics[response.rows[i].dictType]['obj'] = {};
                            dics[response.rows[i].dictType]['arry'] = [];
                            dics[response.rows[i].dictType]['obj'][response.rows[i].dictValue] = response.rows[i];
                            dics[response.rows[i].dictType]['arry'].push({text:response.rows[i].dictLabel?response.rows[i].dictLabel.toString():'',value:response.rows[i].dictValue?parseInt(response.rows[i].dictValue):''});
                        } else {
                            dics[response.rows[i].dictType]['obj'][response.rows[i].dictValue] = response.rows[i];
                            dics[response.rows[i].dictType]['arry'].push({text:response.rows[i].dictLabel?response.rows[i].dictLabel.toString():'',value:response.rows[i].dictValue?parseInt(response.rows[i].dictValue):''});
                        }
                        }
                        commit('SET_DICTS', dics);
                        resolve();
                    }
                }).catch(error => {
                    reject(error)
                })
            })
        }
    }
}

export default common