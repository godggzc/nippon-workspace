<template>
  <div id="app">

    <el-container class="container">
      <el-card class="main">
        <el-row>
          <el-col :span="24">
            <el-input v-model="input" placeholder="请输入待办事项，按Enter键确认" @keyup.enter.native="add(input)"><i slot="suffix" class="el-input__icon el-icon-search"></i></el-input>
          </el-col>
        </el-row>

        <el-row class="item-container">
          <el-col :span="24">
            <div v-for="(todo,index) in todos" :key="index" class="text item">
              <el-checkbox :size="'medium'" :value="todo.completed" :disabled="todo.completed" :checked="todo.completed" @change="completed(todo)"><span :class="{completed:todo.completed}">{{todo.name}}</span></el-checkbox>
              <el-link icon="el-icon-close" :underline="false" @click.stop="remove(todo)"></el-link>
            </div>
          </el-col>
        </el-row>

        <el-row type="flex" justify="center" align="middle" class="oper-container">
          <el-col :span="4" class="oper-total">
            共有{{todos.length}}项待办
          </el-col>
          <el-col :span="16" class="oper-group">
            <el-radio-group v-model="tab">
              <el-radio-button label="all">全部</el-radio-button>
              <el-radio-button label="todo">待处理</el-radio-button>
              <el-radio-button label="completed">已完成</el-radio-button>
            </el-radio-group>
          </el-col>
          <el-col :span="4" class="oper-clear">
            <el-button @click="clear()">清空已完成</el-button>
          </el-col>
        </el-row>
      </el-card>


    </el-container>

  </div>
</template>

<script>
export default {
  name: "Index",

  data() {
    return {
      // 版本号
      todos: [
        { name: '吃早饭', completed: false },
        { name: '上班', completed: false },
        { name: '下班', completed: false },
      ],
      tab: 'all',


    };
  },
  inject: ['completed', 'remove', 'add', 'switchTab', 'clear'],
  watch: {
    tab(newVal) {
      this.switchTab(newVal)
    }
  },
  computed: {
    todolist() {
      let list = this.todos
      if (this.tab === 'todo') {
        list = this.todos.filter(p => p.completed === false)
      } else if (this.tab === 'completed') {
        list = this.todos.filter(p => p.completed === true)
      }
      return list
    }
  },
  methods: {
    completed(todo) {
      todo.completed = true
    },
    remove(todo) {
      var index = this.todos.findIndex(p => p == todo)
      this.todos.splice(index, 1)
    },
    add(name) {

      if (!name || name.length == 0) {
        this.$message({
          showClose: true,
          message: '请输入有效的待办事项',
          type: 'error'
        })
        return
      }

      let todo = { name: name, completed: false }
      this.todos.unshift(todo)
      // 清空输入框
      this.$refs.list.input = ''
    },
    switchTab(tab) {
      this.tab = tab
    },
    clear() {
      this.todos = this.todos.filter(p => p.completed === false)
    } },
  provide() {
    return {
      completed: this.completed,
      remove: this.remove,
      add: this.add,
      switchTab: this.switchTab,
      clear: this.clear
    }
  }
};
</script>

<style scoped lang="scss">
.container {
  height: 100vh;
  background-color: #f4f4f4;
}

.header {
  text-align: center;
  background-color: #409eff;
  line-height: 60px;
  color: #fff;
}

.main {
  width: 900px;
  margin: 0 auto;
}
.text {
  font-size: 14px;
}

.item {
  padding: 12px 0;
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
}
.oper-group {
  text-align: center;
}
.oper-clear {
  text-align: right;
}
.completed {
  text-decoration: line-through;
}

</style>

