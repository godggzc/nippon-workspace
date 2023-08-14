package cn.nicegoose.project.work.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.nicegoose.framework.aspectj.lang.annotation.Excel;
import cn.nicegoose.framework.web.domain.BaseEntity;

/**
 * 代做事项对象 sys_user_todos
 * 
 * @author nicegoose
 * @date 2023-08-10
 */
public class SysUserTodos extends BaseEntity
{
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @TableId(type = IdType.AUTO)
    private Long todoId;

    /** 代做事项简述 */
    @Excel(name = "代做事项简述")
    private String todoName;

    /** 对应用户 */
    @Excel(name = "对应用户")
    private Long userId;

    /** 代做事项详情 */
    @Excel(name = "代做事项详情")
    private String todoComment;

    /** 状态（0代办 1已完成 2删除） */
    @Excel(name = "状态", readConverterExp = "0=代办,1=已完成,2=删除")
    private String todoStatus;

    public void setTodoId(Long todoId) 
    {
        this.todoId = todoId;
    }

    public Long getTodoId() 
    {
        return todoId;
    }
    public void setTodoName(String todoName) 
    {
        this.todoName = todoName;
    }

    public String getTodoName() 
    {
        return todoName;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setTodoComment(String todoComment) 
    {
        this.todoComment = todoComment;
    }

    public String getTodoComment() 
    {
        return todoComment;
    }
    public void setTodoStatus(String todoStatus) 
    {
        this.todoStatus = todoStatus;
    }

    public String getTodoStatus() 
    {
        return todoStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("todoId", getTodoId())
            .append("todoName", getTodoName())
            .append("userId", getUserId())
            .append("todoComment", getTodoComment())
            .append("todoStatus", getTodoStatus())
            .toString();
    }
}
