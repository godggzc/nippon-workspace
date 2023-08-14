<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="代做事项简述" prop="todoName">
        <el-input
          v-model="queryParams.todoName"
          placeholder="请输入代做事项简述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="对应用户" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入对应用户"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="代做事项详情" prop="todoComment">
        <el-input
          v-model="queryParams.todoComment"
          placeholder="请输入代做事项详情"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="todoStatus">
        <el-select v-model="queryParams.todoStatus" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.todo_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['work:todos:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['work:todos:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['work:todos:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['work:todos:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="todosList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="todoId" />
      <el-table-column label="代做事项简述" align="center" prop="todoName" />
      <el-table-column label="对应用户" align="center" prop="userId" />
      <el-table-column label="代做事项详情" align="center" prop="todoComment" />
      <el-table-column label="代做事项状态" align="center" prop="todoStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.todo_status" :value="scope.row.todoStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['work:todos:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['work:todos:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改代做事项对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="代做事项简述" prop="todoName">
          <el-input v-model="form.todoName" placeholder="请输入代做事项简述" />
        </el-form-item>
        <el-form-item label="对应用户" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入对应用户" />
        </el-form-item>
        <el-form-item label="代做事项详情" prop="todoComment">
          <el-input v-model="form.todoComment" placeholder="请输入代做事项详情" />
        </el-form-item>
        <el-form-item label="状态" prop="todoStatus">
          <el-radio-group v-model="form.todoStatus">
            <el-radio
              v-for="dict in dict.type.todo_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTodos, getTodos, delTodos, addTodos, updateTodos } from "@/api/work/todos";

export default {
  name: "Todos",
  dicts: ['todo_status'],
  data() {
    return {
      // 遮罩层
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
      // 代做事项表格数据
      todosList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        todoName: null,
        userId: null,
        todoComment: null,
        todoStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询代做事项列表 */
    getList() {
      this.loading = true;
      listTodos(this.queryParams).then(response => {
        this.todosList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.todoId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加代做事项";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const todoId = row.todoId || this.ids
      getTodos(todoId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改代做事项";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.todoId != null) {
            updateTodos(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTodos(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const todoIds = row.todoId || this.ids;
      this.$modal.confirm('是否确认删除代做事项编号为"' + todoIds + '"的数据项？').then(function() {
        return delTodos(todoIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('work/todos/export', {
        ...this.queryParams
      }, `todos_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
