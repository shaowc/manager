# 后台用户及权限管理

## 数据库设计
1. TCK_MANAGER_EMPLOYEE 后台管理账户
2. TCK_MANAGER_EMPLOYEE_PERMISSION 后台用户权限表 
3. TCK_MANAGER_PERMISSION  后台权限表
4. TCK_MANAGER_EMPLOYEE_OPERATION 后台操作记录


## 权限配置
1. 对于需要登录验证的页面，在执行的Controller方法上加上  @Security
2. 对需要在后台配置的权限，在 @Security 内配置code , code 规则 "{模块名称}:{功能名称}"
3. 系统默认会对@Security 方法生成 code ， 默认code 规则为 "{controler requestMapping}:{method requestMapping}"
4. 如果出现没有权限的情况，请确认系统管理的权限列表中配置了正确的值，同时检查@Security 是否配置了相应的code 