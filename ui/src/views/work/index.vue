<template >
  <div class="app-container home" >
    <el-row :gutter="20">
  <!-- 代办事项col-->
    <el-col :xs="24" :sm="24" :lg="6">
    <el-card shadow="always"   >

     <div slot="header" class="clearfix">
        <span>待办事项</span>
        <el-button
          style="float: right; padding: 3px 0"
          type="text"
          @click="handleAdd"
          v-hasPermi="['workspace:todos:add']"
        >点击添加代办</el-button>
      </div>

      <div class="box-card">
      <div v-for="(todo,index) in todos" :key="index" class="text item">
        <el-checkbox style="position: relative " :size="'medium'" :value="todo.completed" :disabled="todo.completed" :checked="todo.completed" @change="completed(todo)">
          <span  :class="{completed:todo.completed}">{{todo.todoName}}</span>
        </el-checkbox>
        <el-link icon="el-icon-remove" :underline="false" @click.stop="remove(todo)"></el-link>
      </div>
      </div>
      <div >
        <el-row :gutter="20" type="flex" justify="left" align="middle" class="oper-container">
          <el-col :span="6" class="oper-total">
            共有{{todos.length}}项
          </el-col>
          <el-col :span="20"  class="oper-group" >

            <el-radio-group v-model="tab"  size="mini">
              <el-radio-button label="all">全部</el-radio-button>
              <el-radio-button label="todo" >待处理</el-radio-button>
              <el-radio-button label="completed">已完成</el-radio-button>
            </el-radio-group>

          </el-col>


        </el-row>
        <el-row :gutter="20" type="flex" justify="center" align="middle" class="oper-container">
        <el-col :span="12" class="oper-clear">
          <el-button @click="clear()" size="mini">清空已完成</el-button>
        </el-col>
        </el-row>
      </div >



    </el-card>
      </el-col>
      <!-- itop单据col-->
    <el-col :xs="24" :sm="24" :lg="18" >
        <el-card shadow="always"   >
          <div slot="header" class="clearfix">
            <span>itop单据</span>
            <el-button
              style="float: right; padding: 3px 0;"
              color="primary"
              type="text"
              @click="clearAllNotice"

            >清空所有弹窗</el-button>

            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              @click="handleAdd1"
              v-hasPermi="['workspace']"
            >配置itop取值信息</el-button>
          </div>
          <div class="box-card2">
            <el-table v-loading="loading" :data="itopList.slice((currentPage-1)*pagesize,currentPage*pagesize)"
                      size:small
                      :row-style="{height:'35px'}"
                      :cell-style="{padding:'0px'}"
                      style="font-size: 10px">
              <el-table-column label="请求编号" align="center" prop="userRequestId" width="100" fixed>
                <template slot-scope="scope">
                  <a :href="scope.row.hyperlink" target="_blank" style="color: #00afff">{{scope.row.userRequestId}}</a>
                </template>
              </el-table-column>

              <el-table-column label="标题" align="center" prop="title" width="300"/>
              <el-table-column label="所属区域" align="center" prop="region" />
              <el-table-column label="一级分类" align="center" prop="servicefamilyName" />
              <el-table-column label="二级分类" align="center" prop="serviceName" />
              <el-table-column label="请求时间" align="center" prop="reportDate" width="200" />
            </el-table>

            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[ 8, 12, 20]"
              :page-size="pagesize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
            >
            </el-pagination>
                    </div>


        </el-card>
      </el-col>

    </el-row>


    <!-- 添加或修改代做事项对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="300px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="简述" prop="todoName">
          <el-input v-model="form.todoName" placeholder="请输入代做事项简述" />
        </el-form-item>
        <el-form-item label="详情" prop="todoComment">
          <el-input v-model="form.todoComment" placeholder="请输入代做事项详情" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 添加或修改itop用户配置对话框 -->
    <el-dialog :title="title1" :visible.sync="open1" width="500px" append-to-body>

      <el-form ref="form1" :model="form1" :rules="rules1" label-width="80px">
        <el-form-item label="一级分类" prop="servicefamilyName">
          <el-input v-model="form1.servicefamilyName" placeholder="请输入一级分类" />
        </el-form-item>
        <el-form-item label="二级分类" prop="serviceName">
          <el-input v-model="form1.serviceName" placeholder="如采购&技术+主数据+物流运输&HSE&其他" />
        </el-form-item>
        <el-form-item label="所属区域" prop="region">
          <el-input v-model="form1.region" placeholder="如南区+北区+西区+东区+总部" />
        </el-form-item>
        <el-form-item label="所有者" prop="userName">
          <el-input v-model="form1.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="自动同步" prop="syncStatus">
          <el-radio-group v-model="form1.syncStatus">
            <el-radio
              v-for="dict in dict.type.itop_sync_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm1">确 定</el-button>
        <el-button @click="cancel1">取 消</el-button>
      </div>
    </el-dialog>


  </div>

</template>

<script>
import { workspace_itop_list, getItop, delItop, addItop, updateItop } from "@/api/work/itop";
import {getConfigByUser, listConfig, getConfig, delConfig, addConfig, updateConfig,addItopConfigByUser } from "@/api/work/config";

import {addTodoByUser,listTodosByUser, listTodos, getTodos, delTodos, addTodos, updateTodos,autoSetSysUserTodos,deleteSysUserTodosByUserId } from "@/api/work/todos";
export default {
  name: "work_space",
  dicts: ['todo_status','itop_sync_status'],

  data() {
    return {
      // 版本号
      todos: [],
      tab: 'all',
      input: '',
      form: {},
      form1: {},
      // 表单校验
      rules: {
        // 代做事项名称不能为空
        todoName: [
          { required: true, message: "代做事项名称不能为空", trigger: "blur" },
        ],
      },
      rules1: {

      },
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: "",
      // 是否显示加载中// 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // itop信息表格数据
      itopList: [],
      // 弹出层标题
      title1: "",
      // 是否显示弹出层
      open1: false,
      // 查询参数
      queryParams: {
        UserRequestId: null,
        hyperlink: null,
        reportDate: null,
        callerName: null,
        servicefamilyName: null,
        serviceName: null,
        title: null,
        region: null
      },
      timer:null,
      timer_sync_itop : null,//定时器名称
      result :[],
      notifications:[],
      currentPage:1,    // 当前页
      pagesize:8,
    };

  },
  created() {


    this.setTime();
    this.setAuto()
    this.ListTodos()
    this.VNotification()
    this.getConfigs()
    this.auto()

  },

  watch: {
    tab(newVal) {
      this.switchTab(newVal)

    }

  },
  beforeDestroy(){
    clearInterval(this.timer);　　// 清除定时器
    clearInterval(this.timer_sync_itop);　　// 清除定时器
    this.timer = null;
    this.timer_sync_itop = null;
  },


  computed: {


  },
  methods: {
    //
    auto(){
      autoSetSysUserTodos().then(res => {
        if (res.msg === "同步成功") {
          this.ListTodos()
        }
      })
    },
    //getgetConfigByUser
    getConfigs(){
      getConfigByUser().then(response => {
        //如果不存在response.data，则将其添加到itopList中
        if(response.data!=null){
          console.log(response.data)
          this.form1=response.data
          this.queryParams.serviceName=response.data.serviceName
          this.queryParams.region=response.data.region
          this.queryParams.servicefamilyName=response.data.servicefamilyName
          }

        this.getList();
      })
    },

    VNotification(){
      if (window.Notification) {
      // 浏览器通知--window.Notification
      if (Notification.permission == "granted") {
        console.log("允许通知")
      }else if( Notification.permission != "denied"){
        console.log("需要通知权限")
        Notification.requestPermission((permission)=> {});
      }
    } else {
      console.error('浏览器不支持Notification');
    }},

    setAuto(){
      //每隔一分钟运行一次保存方法
      this.timer_sync_itop = setInterval(()=>{
        this.auto();
      },6000)
    },

    setTime(){
      //每隔一分钟运行一次保存方法
      this.timer = setInterval(()=>{
        this.getList();
      },5000)
    },
    // 每页条数改变时触发
    handleSizeChange(pageSize){
      console.log('条数',pageSize);
      this.pagesize = pageSize;           // 在 每次 每页条数触发的时候，将选择中的值 赋值给 data 里面定义的 页显示条目个数
    },

    // 当前页改变时触发
    handleCurrentChange(pageNum){
      console.log('当前页改变时触发',pageNum);
      this.currentPage = pageNum;     // 在每次当前页改变后的值 赋值给 data 里面定义的 当前页
    },

    /** 查询itop信息列表 */
    getList() {
      this.loading = true;
      workspace_itop_list(this.queryParams).then(response => {
        if (this.itopList.length === 0) {
          this.itopList = response.data;
        }
       else {
          response.data.forEach(i => {
            if(this.itopList.some(item =>item.userRequestId == i.userRequestId)){
                i.status=1

              }else{
                //如果response.rows.userRequestId>this.itopList.userRequestId最大值，则将其添加到itopList中

                  console.log(i.userRequestId+"未存在")
                  this.popNotice("新的itop信息编号"+i.userRequestId,"标题："+i.title+"\n"+"分类："+i.serviceName,i.hyperlink)


              }
          }

          )

        }
        this.itopList = response.data;
        this.total = response.data.length;

        this.loading = false;
      });
    },
    //获取代办事项列表
    ListTodos(){
      return listTodosByUser().then(res => {
        this.todos = res.data
        //当todoslist的todo的todoStatus为0时，将其放入todo的completed为false
        this.todos.forEach(todo => {
         // console.log(todo.todoStatus)
          todo.completed = todo.todoStatus !== "0";

          //如果todo.todoName为itop开头
          if(todo.todoName.startsWith("itop")){
            //todo
          }
        })
      })
    },
    //代办事项的弹窗
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加代做事项";
    },
    handleAdd1() {
      this.reset();
      this.open1 = true;
      this.title1 = "配置itop取值信息";
    },
    // 表单重置
    reset() {
      this.form = {
        todoId: null,
        todoName: null,
        userId: null,
        todoComment: null,
        todoStatus: null
      };
      this.resetForm("form");
    },
    // 代办事项完成
    completed(todo) {

      //如果todo.comment为链接，则打开链接
      if(todo.todoComment.startsWith("http")){
        window.open(todo.todoComment)
      }else {
        console.log(todo)
        todo.completed = true
        todo.todoStatus = "1"
        updateTodos(todo).then(res => {
          this.$modal.msgSuccess("恭喜完成代办事项！");
          this.switchTab(this.tab)
        })
      }
    },
      // 删除代办事项
    remove(todo) {
      var todo_id = todo.todoId
      delTodos(todo_id).then(res => {
        this.$modal.msgSuccess("删除成功");
        console.log(res)
        this.switchTab(this.tab)
      })

    },
    // 表单提交
    submitForm() {

      // 表单校验
      if(this.$refs["form"].validate()){
          addTodoByUser(this.form).then(res => {
            this.$modal.msgSuccess("新增成功");
            console.log(res)
            this.ListTodos()
            this.open = false;
            this.reset();
            this.switchTab(this.tab)
          })


      }

    },
    submitForm1() {

      // 表单校验
      if(this.$refs["form1"].validate()){
        addItopConfigByUser(this.form1).then(res => {
          this.$modal.msgSuccess("新增成功");
          console.log(res)
          this.getConfigs()
          this.open1 = false;

        })


      }

    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    cancel1() {
      this.open1 = false;
    },
    //切换事项事件
    switchTab(tab) {
      this.tab = tab
      if (tab === 'all') {
        this.ListTodos()
      } else if (tab === 'todo') {
        this.ListTodos().then(() => {
          this.todos= this.todos.filter(p => p.completed === false)
        })
      } else if (tab === 'completed') {
        this.ListTodos().then(() => {
          this.todos= this.todos.filter(p => p.completed === true)
        })

      }
    },

    // 通知处理
    popNotice(user, content,herf) {
      //如果浏览器不支持Notification，则提醒用户
      if( Notification.permission == "denied"){
        console.log("需要通知权限")
        Notification.requestPermission((permission)=> {});
      }


      let that = this;
      //如果notifications大于5，那将不再弹出notification
      if(this.notifications.length>5){
        return
      }
      if (Notification.permission == "granted") {
        let notification = new Notification(user, {
          body: content,
        });
        //将notification添加到notifications数组中
        this.notifications.push(notification)
        //notification关闭时触发
        notification.onclose = function(e) {
          console.log('关闭通知')
          that.notifications.splice(that.notifications.indexOf(notification),1)

        };
        //设置过期时间
        setTimeout(function() {
          notification.close();
        }, 30000);


        notification.onclick = function(e) {
          that.$nextTick(() => {
            setTimeout(()=>{
              //具体操作
            },500);
          });
          //可直接打开通知notification相关联的tab窗
          window.focus();
          //或者可以自定义跳转的地址
          window.open(herf);
          notification.close();

        };
      }
    },
    //清空所有notification弹窗
    clearAllNotice() {
      //遍历notifications数组，关闭所有notification弹窗
      this.notifications.forEach(notification => {
        console.log(this.notifications)
        notification.close()

      })
    },
    clear(){
      deleteSysUserTodosByUserId().then(res => {
        this.$modal.msgSuccess("清空成功");
        console.log(res)
        this.switchTab(this.tab)
      })

    }




},
  provide() {
    return {
      completed: this.completed,
      remove: this.remove,
      add: this.add,
      switchTab: this.switchTab,

    }
  }
};
</script>


<style scoped lang="scss">



.text {
  font-size: 14px;
}

.item {
  padding: 12px 10px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.item-container,
.oper-container {
  margin-top: 20px;
}
.oper-total {
  text-align: left;
  font-size: 6px;
}
.oper-group {
  text-align: right;
}
.oper-clear {
  text-align: center;
}
.completed {
  margin-top: 10px;
  text-decoration: line-through;

}

.el-card{

}
.box-card2{
  height:500px;
  overflow:auto;
}
.box-card{

  height:405px;
  overflow:auto;
}
.text {
  font-size: 14px;

}

.item {
  margin-bottom: 18px;

}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}


</style>
