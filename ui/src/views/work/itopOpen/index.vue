<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="itop链接" prop="hyperlink">
        <el-input
          v-model="queryParams.hyperlink"
          placeholder="请输入itop链接"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="请求时间" prop="reportDate">
        <el-input
          v-model="queryParams.reportDate"
          placeholder="请输入请求时间"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所有人名称" prop="agentName">
        <el-input
          v-model="queryParams.agentName"
          placeholder="请输入所有人名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="一级分类" prop="servicefamilyName">
        <el-input
          v-model="queryParams.servicefamilyName"
          placeholder="请输入一级分类"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="二级分类" prop="serviceName">
        <el-input
          v-model="queryParams.serviceName"
          placeholder="请输入二级分类"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属区域" prop="region">
        <el-input
          v-model="queryParams.region"
          placeholder="请输入所属区域"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['work:itopOpen:add']"
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
          v-hasPermi="['work:itopOpen:edit']"
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
          v-hasPermi="['work:itopOpen:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['work:itopOpen:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="itopOpenList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="请求编号" align="center" prop="userRequestId" />
      <el-table-column label="itop链接" align="center" prop="hyperlink" />
      <el-table-column label="请求时间" align="center" prop="reportDate" />
      <el-table-column label="所有人名称" align="center" prop="agentName" />
      <el-table-column label="一级分类" align="center" prop="servicefamilyName" />
      <el-table-column label="二级分类" align="center" prop="serviceName" />
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="所属区域" align="center" prop="region" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['work:itopOpen:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['work:itopOpen:remove']"
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

    <!-- 添加或修改itop信息未处理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="itop链接" prop="hyperlink">
          <el-input v-model="form.hyperlink" placeholder="请输入itop链接" />
        </el-form-item>
        <el-form-item label="请求时间" prop="reportDate">
          <el-input v-model="form.reportDate" placeholder="请输入请求时间" />
        </el-form-item>
        <el-form-item label="所有人名称" prop="agentName">
          <el-input v-model="form.agentName" placeholder="请输入所有人名称" />
        </el-form-item>
        <el-form-item label="一级分类" prop="servicefamilyName">
          <el-input v-model="form.servicefamilyName" placeholder="请输入一级分类" />
        </el-form-item>
        <el-form-item label="二级分类" prop="serviceName">
          <el-input v-model="form.serviceName" placeholder="请输入二级分类" />
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="所属区域" prop="region">
          <el-input v-model="form.region" placeholder="请输入所属区域" />
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
import { listItopOpen, getItopOpen, delItopOpen, addItopOpen, updateItopOpen } from "@/api/work/itopOpen";

export default {
  name: "ItopOpen",
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
      // itop信息未处理表格数据
      itopOpenList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hyperlink: null,
        reportDate: null,
        agentName: null,
        servicefamilyName: null,
        serviceName: null,
        title: null,
        region: null
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
    /** 查询itop信息未处理列表 */
    getList() {
      this.loading = true;
      listItopOpen(this.queryParams).then(response => {
        this.itopOpenList = response.rows;
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
        userRequestId: null,
        hyperlink: null,
        reportDate: null,
        agentName: null,
        servicefamilyName: null,
        serviceName: null,
        title: null,
        region: null
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
      this.ids = selection.map(item => item.userRequestId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加itop信息未处理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const userRequestId = row.userRequestId || this.ids
      getItopOpen(userRequestId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改itop信息未处理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.userRequestId != null) {
            updateItopOpen(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addItopOpen(this.form).then(response => {
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
      const userRequestIds = row.userRequestId || this.ids;
      this.$modal.confirm('是否确认删除itop信息未处理编号为"' + userRequestIds + '"的数据项？').then(function() {
        return delItopOpen(userRequestIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('work/itopOpen/export', {
        ...this.queryParams
      }, `itopOpen_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
