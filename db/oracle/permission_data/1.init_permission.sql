insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('1001', '前端OPTIONS', '/**', 'OPTIONS', 1, null);

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2001', '用户添加', '/user-center/v2/user', 'POST', 2, '用户中心—用户管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2002', '用户修改', '/user-center/v2/user', 'PUT', 2, '用户中心—用户管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2003', '用户删除', '/user-center/v2/user/*', 'DELETE', 2, '用户中心—用户管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2004', '用户分页查询', '/user-center/v2/user', 'GET', 2, '用户中心—用户管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2005', '用户详情查询', '/user-center/v2/user/detail', 'GET', 2, '用户中心—用户管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2006', '用户修改密码', '/user-center/v2/user/password', 'POST', 3, '用户中心—用户管理—公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2007', '管理员重置密码', '/user-center/v2/user/password', 'PUT', 2, '用户中心—用户管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2008', '查询加钞人员用户列表', '/user-center/v2/user/addnotes', 'GET', 2, '用户中心—用户管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2010', '菜单列表查询', '/user-center/v2/menu', 'GET', 3, '用户中心—菜单—公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('20101', '接口列表查询', '/user-center/v2/menu/permission', 'GET', 3, '用户中心—菜单—公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('20102', '查询菜单接口映射', '/user-center/v2/menu/*', 'GET', 3, '用户中心—菜单—公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('20103', '更新菜单接口映射', '/user-center/v2/menu/*', 'PUT', 3, '用户中心—菜单—公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2011', '角色添加', '/user-center/v2/role', 'POST', 2, '用户中心—角色管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2012', '角色修改', '/user-center/v2/role', 'PUT', 2, '用户中心—角色管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2013', '角色删除', '/user-center/v2/role/*', 'DELETE', 2, '用户中心—角色管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2014', '角色分页查询', '/user-center/v2/role', 'GET', 2, '用户中心—角色管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2015', '角色详情查询', '/user-center/v2/role/*', 'GET', 2, '用户中心—角色管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2016', '查询下属级别角色列表', '/user-center/v2/role/grade', 'GET', 2, '用户中心—角色管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2020', '推送给某用户', '/push-server/v2/info/user', '*', 3, '推送服务');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2021', '推送给所有人', '/push-server/v2/info/all', '*', 3, '推送服务');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2022', '推送给角色', '/push-server/v2/info/role', '*', 3, '推送服务');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2023', 'websocket端点', '/push-server/v2/websocket', '*', 3, '推送服务');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2024', '线路推送信息查询', '/push-server/v2/pushServer', 'GET', 2, '推送信息查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2030', '收藏菜单查询', '/user-center/v2/menuCollect', 'GET', 3, '用户中心：快捷菜单查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2031', '收藏菜单更新', '/user-center/v2/menuCollect', 'POST', 3, '用户中心：快捷菜单查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2040', '用户操作日志日志分页查询', '/user-center/v2/userLog', 'GET', 2, '用户中心：用户日志管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2101', '机构修改', '/channel-center/v2/org', 'PUT', 2, '渠道中心—机构管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2102', '机构添加', '/channel-center/v2/org', 'POST', 2, '渠道中心—机构管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2103', '机构列表查询', '/channel-center/v2/org', 'GET', 2, '渠道中心—机构管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2104', '机构详情查询', '/channel-center/v2/org/*', 'GET', 2, '渠道中心—机构管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2105', '机构删除', '/channel-center/v2/org/*', 'DELETE', 2, '渠道中心—机构管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2106', '查询机构树的下一级', '/channel-center/v2/org/children', 'GET', 3, '渠道中心—机构管理—公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2107', '查询全部机构等级', '/channel-center/v2/org/grade', 'GET', 3, '渠道中心—机构管理—公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2108', '模糊查询机构树', '/channel-center/v2/org/fuzzy', 'GET', 3, '渠道中心—机构管理—公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2111', '系统参数修改', '/channel-center/v2/param', 'PUT', 2, '渠道中心—系统参数管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2112', '系统参数添加', '/channel-center/v2/param', 'POST', 2, '渠道中心—系统参数管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2113', '系统参数删除', '/channel-center/v2/param/*', 'DELETE', 2, '渠道中心—系统参数管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2114', '系统参数列表查询', '/channel-center/v2/param', 'GET', 2, '渠道中心—系统参数管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2115', '根据名称查询系统参数', '/channel-center/v2/param/name', 'GET', 2, '渠道中心—系统参数管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2116', '查询全部系统参数类型', '/channel-center/v2/param/type', 'GET', 2, '渠道中心—系统参数管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2121', '查询金库的所有设备', '/channel-center/v2/dev/clrCenter', 'GET', 2, '渠道中心—设备信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2122', '设备分页查询', '/channel-center/v2/dev', 'GET', 2, '渠道中心—设备信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2123', '设备修改', '/channel-center/v2/dev', 'PUT', 2, '渠道中心—设备信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2124', '设备新增', '/channel-center/v2/dev', 'POST', 2, '渠道中心—设备信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2125', '设备删除', '/channel-center/v2/dev/*', 'DELETE', 2, '渠道中心—设备信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2126', '查询设备类型列表', '/channel-center/v2/dev/catalog', 'GET', 2, '渠道中心—设备信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2127', '修改设备加钞周期', '/addnote/v2/modDevClrTime', 'PUT', 2, '渠道中心—设备信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2131', '金库列表查询', '/channel-center/v2/clrCenter', 'GET', 2, '渠道中心—金库管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2132', '金库的网点列表查询', '/channel-center/v2/clrCenter/*', 'GET', 2, '渠道中心—金库管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2141', '设备品牌列表查询', '/channel-center/v2/dev/vendor', 'GET', 2, '渠道中心—设备品牌管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2142', '设备品牌添加', '/channel-center/v2/dev/vendor', 'POST', 2, '渠道中心—设备品牌管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2143', '设备品牌修改', '/channel-center/v2/dev/vendor', 'PUT', 2, '渠道中心—设备品牌管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2144', '设备品牌删除', '/channel-center/v2/dev/vendor/*', 'DELETE', 2, '渠道中心—设备品牌管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2151', '设备维护商列表查询', '/channel-center/v2/dev/company', 'GET', 2, '渠道中心—设备维护商管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2152', '设备维护商添加', '/channel-center/v2/dev/company', 'POST', 2, '渠道中心—设备维护商管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2153', '设备维护商修改', '/channel-center/v2/dev/company', 'PUT', 2, '渠道中心—设备维护商管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2154', '批量导入服务商信息', '/channel-center/v2/dev/company/import', 'POST', 2, '渠道中心—设备维护商管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2155', '设备维护商删除', '/channel-center/v2/dev/company/*', 'DELETE', 2, '渠道中心—设备维护商管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2161', '设备型号列表查询', '/channel-center/v2/dev/type', 'GET', 2, '渠道中心—设备型号管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2162', '设备型号添加', '/channel-center/v2/dev/type', 'POST', 2, '渠道中心—设备型号管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2163', '设备型号修改', '/channel-center/v2/dev/type', 'PUT', 2, '渠道中心—设备型号管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2164', '设备型号删除', '/channel-center/v2/dev/type/*', 'DELETE', 2, '渠道中心—设备型号管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2165', '查询钞箱', '/channel-center/v2/cassette', 'GET', 2, '综合管理：钞箱管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2166', '添加钞箱', '/channel-center/v2/cassette', 'POST', 2, '综合管理：钞箱管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2167', '修改钞箱', '/channel-center/v2/cassette', 'PUT', 2, '综合管理：钞箱管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2168', '删除钞箱', '/channel-center/v2/cassette/*', 'DELETE', 2, '综合管理：钞箱管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2169', '查询钞袋', '/channel-center/v2/cassettebag', 'GET', 2, '综合管理：钞袋管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2170', '添加钞袋', '/channel-center/v2/cassettebag', 'POST', 2, '综合管理：钞袋管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2171', '修改钞袋', '/channel-center/v2/cassettebag', 'PUT', 2, '综合管理：钞袋管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2172', '删除钞袋', '/channel-center/v2/cassettebag/*', 'DELETE', 2, '综合管理：钞袋管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2173', '查询标签', '/channel-center/v2/tag', 'GET', 2, '综合管理：标签管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2174', '添加标签', '/channel-center/v2/tag', 'POST', 2, '综合管理：标签管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2175', '修改标签', '/channel-center/v2/tag', 'PUT', 2, '综合管理：标签管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2176', '删除标签', '/channel-center/v2/tag/*', 'DELETE', 2, '综合管理：标签管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2177', '添加标签', '/channel-center/v2/tag/import', 'POST', 2, '综合管理：标签管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2180', '查询网点线路运行图', '/channel-center/v2/networkLineRunMap', 'GET', 2, '渠道中心：网点线路运行图');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2181', '添加网点线路运行图', '/channel-center/v2/networkLineRunMap', 'POST', 2, '渠道中心：网点线路运行图');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2182', '删除网点线路运行图', '/channel-center/v2/networkLineRunMap', 'PUT', 2, '渠道中心：网点线路运行图');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2183', '查询网点线路运行图详情', '/channel-center/v2/networkLineRunMap/*', 'GET', 2, '渠道中心：网点线路运行图');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2184', '泊车引导', '/security-center/v2/parkingManage', 'POST', 2, '安防中心：泊车引导');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2190', '添加款箱基础信息', '/channel-center/v2/entity/import', 'POST', 2, '综合管理：款箱基础信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2191', '查询款箱基础信息', '/channel-center/v2/entity', 'GET', 2, '综合管理：款箱基础信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2192', '添加款箱基础信息', '/channel-center/v2/entity', 'POST', 2, '综合管理：款箱基础信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2193', '修改款箱基础信息', '/channel-center/v2/entity', 'PUT', 2, '综合管理：款箱基础信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2194', '删除款箱基础信息', '/channel-center/v2/entity/*', 'DELETE', 2, '综合管理：款箱基础信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2201', '地图中查询网点', '/insight/v2/gis/business/pointInMap', 'GET', 2, '数据洞察模块—GIS');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2202', '根据城市查询网点列表', '/insight/v2/gis/business/points', 'GET', 2, '数据洞察模块—GIS');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2203', '根据条件查询排名', '/insight/v2/gis/business/ranking', 'GET', 2, '数据洞察模块—GIS');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2204', '查询网点模板', '/insight/v2/gis/template', 'GET', 2, '数据洞察模块—GIS');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2205', '添加更新网点模板', '/insight/v2/gis/template', 'POST', 2, '数据洞察模块—GIS');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2206', '根据设备号查询CASE', '/insight/v2/gis/business/caseList', 'GET', 2, '数据洞察模块—GIS');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2207', '根据设备号查询CASE类型', '/insight/v2/gis/business/caseTypes', 'GET', 2, '数据洞察模块—GIS');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2208', '根据设备号查询设备信息', '/insight/v2/gis/business/devsInfo', 'GET', 2, '数据洞察模块—GIS');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2211', '图表组件列表查询', '/insight/v2/chart', 'GET', 2, '数据洞察模块—图表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2212', '修改图表', '/insight/v2/chart', 'PUT', 2, '数据洞察模块—图表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2213', '获取图表信息', '/insight/v2/chart/*', 'GET', 2, '数据洞察模块—图表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2214', '删除图表', '/insight/v2/chart/*', 'DELETE', 2, '数据洞察模块—图表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2215', '查询组件创建者列表', '/insight/v2/chart/creator', 'GET', 2, '数据洞察模块—图表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2221', '图表组件列表查询', '/insight/v2/chartsDevelop', 'GET', 2, '数据洞察模块—图表开发者');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2222', '图表组件添加', '/insight/v2/chartsDevelop', 'POST', 2, '数据洞察模块—图表开发者');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2223', '图表组件预览', '/insight/v2/chartsDevelop/preview', 'POST', 2, '数据洞察模块—图表开发者');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2224', '查询数据服务列表', '/insight/v2/chartsDevelop/serviceList', 'GET', 2, '数据洞察模块—图表开发者');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2225', '查询组件主题列表', '/insight/v2/chartsDevelop/subjects', 'GET', 2, '数据洞察模块—图表开发者');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2231', '报表添加', '/insight/v2/report', 'POST', 2, '数据洞察模块—报表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2232', '报表删除', '/insight/v2/report/*', 'DELETE', 2, '数据洞察模块—报表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2233', '报表导出', '/insight/v2/report/exp', 'POST', 2, '数据洞察模块—报表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2234', '查看报表文件详情', '/insight/v2/report/fileDetail', 'GET', 2, '数据洞察模块—报表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2235', '查询报表所有分组', '/insight/v2/report/group', 'GET', 2, '数据洞察模块—报表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2236', '获取所有报表列表', '/insight/v2/report/model', 'GET', 2, '数据洞察模块—报表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2237', '报表列表分页查询', '/insight/v2/report/page', 'GET', 2, '数据洞察模块—报表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2238', '报表查询', '/insight/v2/report/params', 'POST', 2, '数据洞察模块—报表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2239', '报表配置项查询', '/insight/v2/report/property', 'GET', 2, '数据洞察模块—报表');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2241', '明细查询添加', '/insight/v2/detailDevelop/detail', 'POST', 2, '数据洞察模块—明细查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2242', '明细查询导出', '/insight/v2/detailDevelop/export', 'POST', 2, '数据洞察模块—明细查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2243', '明细查询分组列表', '/insight/v2/detailDevelop/groups', 'GET', 2, '数据洞察模块—明细查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2244', '明细查询列表', '/insight/v2/detailDevelop/model', 'GET', 2, '数据洞察模块—明细查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2245', '明细查询保存', '/insight/v2/detailDevelop/model', 'POST', 2, '数据洞察模块—明细查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2246', '明细详情查询', '/insight/v2/detailDevelop/model/*', 'GET', 2, '数据洞察模块—明细查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2247', '数据服务列表', '/insight/v2/detailDevelop/serviceList', 'GET', 2, '数据洞察模块—明细查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2248', '明细查询信息', '/insight/v2/detailQuery', 'GET', 2, '数据洞察模块—明细查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2249', '明细查询删除', '/insight/v2/detailQuery', 'DELETE', 2, '数据洞察模块—明细查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2251', '获取用户自定义配置', '/insight/v2/view/*', 'GET', 3, '数据洞察模块—用户配置');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2252', '更新用户驾驶舱配置', '/insight/v2/view/default', 'PUT', 2, '数据洞察模块—用户配置');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2253', '获取用户主题配置', '/insight/v2/view/subject', 'GET', 2, '数据洞察模块—用户配置');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2254', '获取saikuToken', '/insight/v2/saikuToken/*', 'GET', 2, '数据洞察模块—多维分析');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2261', '数据洞察模块—大屏临时接口', '/insight/v2/portal/**', '*', 3, '数据洞察模块—大屏临时接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2271', '分页查询岗位信息', '/channel-center/v2/job', 'GET', 2, '渠道中心：岗位管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2272', '新增岗位信息', '/channel-center/v2/job', 'POST', 2, '渠道中心：岗位管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2273', '修改岗位信息', '/channel-center/v2/job', 'PUT', 2, '渠道中心：岗位管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2274', '删除岗位信息', '/channel-center/v2/job/*', 'DELETE', 2, '渠道中心：岗位管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2275', '查询全部岗位信息', '/channel-center/v2/job/all', 'GET', 2, '渠道中心：岗位管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2280', '分页查询上门收款线路安排', '/channel-center/v2/callCustomerLineRun', 'GET', 2, '渠道中心：上门收款线路排班管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2281', '查询上门收款线路安排详情', '/channel-center/v2/callCustomerLineRun/details', 'GET', 2, '渠道中心：上门收款线路排班管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2282', '覆盖生成线路运行图', '/channel-center/v2/callCustomerLineRun', 'POST', 2, '渠道中心：上门收款线路排班管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2283', '修改线路运行图', '/channel-center/v2/callCustomerLineRun', 'PUT', 2, '渠道中心：上门收款线路排班管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2290', '上门客户信息查询', '/channel-center/v2/callCustomer', 'GET', 2, '渠道中心：上门客户管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2291', '上门客户信息增加', '/channel-center/v2/callCustomer', 'POST', 2, '渠道中心：上门客户管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2292', '上门客户信息修改', '/channel-center/v2/callCustomer', 'PUT', 2, '渠道中心：上门客户管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2293', '上门客户信息删除', '/channel-center/v2/callCustomer', 'DELETE', 2, '渠道中心：上门客户管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2294', '上门收款周期查询', '/channel-center/v2/callCustomerTime/*', 'GET', 2, '渠道中心：上门客户周期管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2295', '上门收款周期修改', '/channel-center/v2/modCallCustomerTime', 'PUT', 2, '渠道中心：上门客户周期管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2301', '添加加钞计划审核', '/addnote/v2/audit', 'POST', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2302', '修改加钞金额（金额调整）', '/addnote/v2/cash', 'PUT', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2303', '设备金额预测', '/addnote/v2/cash/forecast', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2304', '查询清机中心列表', '/addnote/v2/clrCenter', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2305', '获取清分中心详细信息', '/addnote/v2/clrCenter/*', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2306', '获取清分中心下所有网点', '/addnote/v2/clrCenter/netpoints/*', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2307', '查询设备信息及使用信息', '/addnote/v2/dev', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2308', '保存加钞设备', '/addnote/v2/dev', 'POST', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2309', '修改加钞设备（设备调整）', '/addnote/v2/dev', 'PUT', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2310', '加钞设备分析', '/addnote/v2/dev/addnotesDevAnalyse', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2311', '查询维护型设备列表', '/addnote/v2/dev/maintain', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2312', '查询决定型设备列表', '/addnote/v2/dev/must', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2313', '查询预测型设备列表', '/addnote/v2/dev/predict', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2314', '加钞任务查询', '/task/v2/task', 'GET', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2315', '加钞计划出单', '/task/v2/task/task', 'POST', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2316', '加钞任务详细信息查询', '/task/v2/task/detail/*', 'GET', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2317', '删除加钞任务', '/addnote/v2/dispatch/*', 'DELETE', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2318', '设备分组信息修改', '/addnote/v2/group', 'PUT', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2319', '计划分组信息查询', '/addnote/v2/group/*', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2320', '设备分组详细信息查询', '/addnote/v2/group/detail', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2321', '规划两点间线路', '/addnote/v2/group/directionRoute', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2322', '查询分组下的网点信息', '/addnote/v2/group/netpoints', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2323', '查询加钞计划分组下的线路', '/addnote/v2/group/route', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2324', '设备分组并规划线路', '/addnote/v2/group/tsp', 'POST', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2325', '分组线路信息修改', '/addnote/v2/group/tsp', 'PUT', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2326', '查询加钞计划', '/addnote/v2/plan', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2327', '添加加钞计划', '/addnote/v2/plan', 'POST', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2328', '查询加钞计划详情', '/addnote/v2/plan/*', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2329', '删除加钞计划', '/addnote/v2/plan/*', 'DELETE', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2330', '查询加钞计划内容', '/addnote/v2/planDetail/*', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2331', '分配人员', '/task/v2/task/operator', 'PUT', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2332', '取消加钞任务', '/task/v2/task/cancel/*', 'PUT', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2333', '加钞计划退审', '/addnote/v2/refuse', 'POST', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2334', '查询计划型设备列表', '/addnote/v2/dev/runPlan', 'GET', 2, '加钞模块：加钞计划管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2335', '查询自己当天可执行任务', '/task/v2/task/ownTask', 'GET', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2336', '查询自己当天可执行任务明细', '/task/v2/task/ownTaskDetail/*', 'GET', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2337', '根据设备号查询当天任务单查询详情', '/task/v2/task/qryTask/qryByCustomerNo', 'GET', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2338', '任务单导出', '/task/v2/task/exportTaskExcel', 'POST', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2339', '查询任务单导出信息', '/task/v2/task/exportTask', 'GET', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2340', '根据线路编号查询任务单明细', '/task/v2/task/qryTask/qryByLineNo', 'GET', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2341', '特殊加钞规则分页查询', '/addnote/v2/devRuleExec', 'GET', 2, '加钞模块：执行特殊规则');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2342', '添加特殊加钞规则执行', '/addnote/v2/devRuleExec', 'POST', 2, '加钞模块：执行特殊规则');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2343', '删除执行规则信息', '/addnote/v2/devRuleExec', 'DELETE', 2, '加钞模块：执行特殊规则');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2344', '根据容器编号查询任务单明细', '/task/v2/task/qryTask/qryBycontainerNo', 'GET', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2345', '查询当天可领现的任务明细', '/task/v2/task/ownReceiptTaskDetail/*', 'GET', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2346', '根据设备号查询设备上一个任务使用的容器', '/task/v2/task/qryPreviousContain/*', 'GET', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2347', '查询可执行线路', '/task/v2/task/qryLineNoList', 'GET', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2348', '根据网点查询款箱号', '/task/v2/task/qryContainerNoList/*', 'GET', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2349', '根据线路编号查询笼车明细（出库）', '/task/v2/task/qryShelfDetailByLineNo', 'GET', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2351', '查询特殊规则', '/addnote/v2/rule', 'GET', 2, '加钞模块：特殊规则管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2352', '添加特殊规则', '/addnote/v2/rule', 'POST', 2, '加钞模块：特殊规则管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2353', '修改特殊规则', '/addnote/v2/rule', 'PUT', 2, '加钞模块：特殊规则管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2354', '查询特殊规则详情', '/addnote/v2/rule/*', 'GET', 2, '加钞模块：特殊规则管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2355', '删除特殊规则', '/addnote/v2/rule/*', 'DELETE', 2, '加钞模块：特殊规则管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2361', '路程信息列表查询', '/addnote/v2/matrix', 'GET', 2, '加钞模块：网点/设备路程管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2362', '路程信息关联', '/addnote/v2/matrix/linkPath', 'POST', 2, '加钞模块：网点/设备路程管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2363', '获取清分中心的已关联路径数', '/addnote/v2/matrix/linkPath', 'GET', 2, '加钞模块：网点/设备路程管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2364', '查询清分中心的是否正在关联', '/addnote/v2/matrix/netpointStatus/*', 'GET', 2, '加钞模块：网点/设备路程管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2371', '修改设备参数', '/addnote/v2/devConfig', 'PUT', 2, '加钞模块：设备选择参数配置');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2372', '查询设备参数', '/addnote/v2/devConfig/*', 'GET', 2, '加钞模块：设备选择参数配置');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2381', '查询加钞线路', '/addnote/v2/line', 'GET', 2, '加钞模块：加钞线路管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2382', '添加加钞线路', '/addnote/v2/line', 'POST', 2, '加钞模块：加钞线路管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2383', '修改加钞线路', '/addnote/v2/line', 'PUT', 2, '加钞模块：加钞线路管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2384', '删除加钞线路', '/addnote/v2/line/*', 'DELETE', 2, '加钞模块：加钞线路管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2385', '查询加钞线路详情', '/addnote/v2/line/*', 'GET', 2, '加钞模块：加钞线路管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2386', '获取清分中心下所有线路', '/addnote/v2/line/clrCenter', 'GET', 2, '加钞模块：加钞线路管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2387', '根据日期、清分中心查询所有线路', '/addnote/v2/line/dateAndClrCenter', 'GET', 2, '加钞模块：加钞线路管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2391', '查询线路运行图', '/addnote/v2/lineRunMap', 'GET', 2, '加钞模块：设备线路运行图');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2392', '添加线路运行图', '/addnote/v2/lineRunMap', 'POST', 2, '加钞模块：设备线路运行图');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2393', '修改线路运行图', '/addnote/v2/lineRunMap', 'PUT', 2, '加钞模块：设备线路运行图');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2394', '查询线路运行图详情', '/addnote/v2/lineRunMap/*', 'GET', 2, '加钞模块：设备线路运行图');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2395', '查询单台设备的清机周期信息', '/addnote/v2/devClrTime/*', 'GET', 2, '加钞模块：设备加钞周期管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2401', '查询金库特殊日期配钞', '/addnote/v2/spDateCoeff', 'GET', 2, '加钞模块：金库特殊日期配钞设置');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2402', '增加金库特殊日期配钞设置', '/addnote/v2/spDateCoeff', 'POST', 2, '加钞模块：金库特殊日期配钞设置');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2403', '修改金库特殊日期配钞信息', '/addnote/v2/spDateCoeff', 'PUT', 2, '加钞模块：金库特殊日期配钞设置');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2404', '删除金库特殊日期配钞信息', '/addnote/v2/spDateCoeff', 'DELETE', 2, '加钞模块：金库特殊日期配钞设置');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2501', '任务单拆分', '/task/v2/task/split', 'POST', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2502', '任务单合并', '/task/v2/task/merge', 'POST', 2, '任务中心：加钞任务单管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2601', '实物综合信息查询', '/channel-center/v2/entityRealTimeDetail', 'GET', 2, '综合管理：实物综合信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2602', '实物综合信息修改', '/channel-center/v2/entityRealTimeDetail', 'PUT', 2, '综合管理：实物综合信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2603', '实物综合信息添加', '/channel-center/v2/entityRealTimeDetail', 'POST', 2, '综合管理：实物综合信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2604', '实物综合信息删除', '/channel-center/v2/entityRealTimeDetail/*', 'DELETE', 2, '综合管理：实物综合信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3001', 'PC端,登陆', '/auth/callback/login', 'POST', 3, '公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3002', 'PC端,刷新token', '/auth/callback/refresh', 'POST', 3, '公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3003', '授权码token', '/auth/callback/token', 'POST', 3, '公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3004', 'token', '/oauth/token', '*', 3, '公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3005', 'swagger', '/swagger-ui.html', '*', 3, '公共接口—接口文档');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3006', 'swagger', '/swagger-ui.html/**', '*', 3, '公共接口—接口文档');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3007', 'swagger', '/v2/**', '*', 3, '公共接口—接口文档');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3008', 'swagger', '/webjars/**', '*', 3, '公共接口—接口文档');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3009', 'swagger', '/swagger-resources/**', '*', 3, '公共接口—接口文档');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3010', 'swagger', '/swagger-resources', '*', 3, '公共接口—接口文档');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3011', '刷新权限全局缓存', '/auth/v2/cache', 'GET', 3, '公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3012', '登出', '/auth/callback/logout', 'POST', 3, '公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3013', '移动端,登陆', '/auth/callback/login/mobile', 'POST', 3, '公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3014', '移动端,刷新token', '/auth/callback/refresh/mobile', 'POST', 3, '公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3015', 'knife4j', '/doc.html', '*', 3, '公共接口—接口文档');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3016', 'knife4j', '/doc.html/**', '*', 3, '公共接口—接口文档');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('3017', '监控信息', '/actuator/**', '*', 3, '公共接口');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4001', '查询服务商', '/tauro/v2/devService', 'GET', 2, '流转模块：服务商管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4002', '添加服务商', '/tauro/v2/devService', 'POST', 2, '流转模块：服务商管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4003', '修改服务商', '/tauro/v2/devService', 'PUT', 2, '流转模块：服务商管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4004', '删除服务商', '/tauro/v2/devService/*', 'DELETE', 2, '流转模块：服务商管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4013', '查询标签读写器', '/channel-center/v2/tagReader', 'GET', 2, '综合管理：标签读写器管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4014', '添加标签读写器', '/channel-center/v2/tagReader', 'POST', 2, '综合管理：标签读写器管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4015', '修改标签读写器', '/channel-center/v2/tagReader', 'PUT', 2, '综合管理：标签读写器管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4016', '删除标签读写器', '/channel-center/v2/tagReader/*', 'DELETE', 2, '综合管理：标签读写器管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4017', '查询运钞车', '/tauro/v2/armoredcar', 'GET', 2, '流转模块：运钞车管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4018', '添加运钞车', '/tauro/v2/armoredcar', 'POST', 2, '流转模块：运钞车管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4019', '修改运钞车', '/tauro/v2/armoredcar', 'PUT', 2, '流转模块：运钞车管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4020', '删除运钞车', '/tauro/v2/armoredcar/*', 'DELETE', 2, '流转模块：运钞车管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4021', '查询外包人员信息', '/channel-center/v2/outsourcing', 'GET', 2, '综合管理：外包人员管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4022', '添加外包人员信息', '/channel-center/v2/outsourcing', 'POST', 2, '综合管理：外包人员管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4023', '修改外包人员信息', '/channel-center/v2/outsourcing', 'PUT', 2, '综合管理：外包人员管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4024', '删除外包人员信息', '/channel-center/v2/outsourcing/*', 'DELETE', 2, '综合管理：外包人员管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4025', '导入外包人员信息', '/channel-center/v2/outsourcing/import', 'POST', 2, '综合管理：外包人员管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4031', '查询车辆', '/channel-center/v2/car', 'GET', 2, '综合管理：车辆管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4032', '添加车辆', '/channel-center/v2/car', 'POST', 2, '综合管理：车辆管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4033', '修改车辆', '/channel-center/v2/car', 'PUT', 2, '综合管理：车辆管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4034', '删除车辆', '/channel-center/v2/car/*', 'DELETE', 2, '综合管理：车辆管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4035', '导入车辆', '/channel-center/v2/car/import', 'POST', 2, '综合管理：车辆管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4040', '押运人员排班信息分页查询', '/channel-center/v2/outsourcingLineMap', 'GET', 2, '综合管理：押运人员排班管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4041', '押运人员排班信息覆盖生成', '/channel-center/v2/outsourcingLineMap', 'POST', 2, '综合管理：押运人员排班管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4042', '押运人员排班信息修改', '/channel-center/v2/outsourcingLineMap', 'PUT', 2, '综合管理：押运人员排班管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4043', '押运人员排班信息删除', '/channel-center/v2/outsourcingLineMap/*', 'DELETE', 2, '综合管理：押运人员排班管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4044', '押运人员排班信息导出', '/channel-center/v2/outsourcingLineMap/export', 'POST', 2, '综合管理：押运人员排班管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4111', '查询手持机领用记录', '/tauro/v2/readerInfoFlow', 'GET', 2, '流转模块：手持机领用管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4112', '添加手持机领用记录', '/tauro/v2/readerInfoFlow', 'POST', 2, '流转模块：手持机领用管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4113', '修改手持机领用记录', '/tauro/v2/readerInfoFlow', 'PUT', 2, '流转模块：手持机领用管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4114', '查询手持机领用记录详情', '/tauro/v2/readerInfoFlow/detail', 'GET', 2, '流转模块：手持机领用管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4115', '审核手持机领用记录', '/tauro/v2/readerInfoFlow/audit', 'POST', 2, '流转模块：手持机领用管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4116', '归还手持机', '/tauro/v2/readerInfoFlow/return', 'POST', 2, '流转模块：手持机领用管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4117', '删除手持机领用记录', '/tauro/v2/readerInfoFlow/*', 'DELETE', 2, '流转模块：手持机领用管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4126', '查询流程任务信息', '/tauro/v2/taskProcess', 'GET', 2, '物流中心：任务流转');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4127', '增加手持机坐标信息', '/tauro/v2/taskProcess/coords', 'POST', 2, '物流中心：任务流转');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4128', '查询手持机在流转工程中的位置记录', '/tauro/v2/taskProcess/coords/*', 'GET', 2, '物流中心：任务流转');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4129', '查询流程任务信息详情', '/tauro/v2/taskProcess/*', 'GET', 2, '物流中心：任务流转');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4130', '查询设备列表和是否已经加钞', '/tauro/v2/dispatch/detail/*', 'GET', 2, '流转模块：任务流转记录');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4131', '钞袋出库', '/tauro/v2/outStorage', 'POST', 2, '物流中心：实物出库');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4132', '虚拟签收', '/tauro/v2/sign', 'POST', 2, '物流中心：虚拟签收');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4133', '回收', '/tauro/v2/recover', 'POST', 2, '物流中心：回收');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4134', '入库', '/tauro/v2/in', 'POST', 2, '物流中心：入库');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('4135', '经警接库', '/tauro/v2/convoyOut', 'POST', 2, '物流中心：经警接库');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5001', '配钞', '/clear-center/v2/sort/loadNote', 'POST', 3, '钞处中心：配钞');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5002', '配钞信息查询', '/clear-center/v2/sort/loadNote/*', 'GET', 3, '钞处中心：配钞信息查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5003', '分页查询配钞信息', '/clear-center/v2/sort/loadNote', 'GET', 3, '钞处中心：配钞信息查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5004', 'ATM现金清点', '/clear-center/v2/sort/inventory', 'POST', 3, '钞处中心：现金清分清点');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5005', 'ATM现金清点信息查询', '/clear-center/v2/sort/inventory/*', 'GET', 3, '钞处中心：现金清分清点');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5006', 'ATM现金清点信息分页查询', '/clear-center/v2/sort/loadCheckNote', 'GET', 3, '钞处中心：现金清分清点');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5007', '现金入库', '/clear-center/v2/sort/inventory/cashIn', 'POST', 3, '钞处中心：现金清分清点');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5008', '配款领现', '/clear-center/v2/sort/inventory/cashDistribution', 'POST', 3, '钞处中心：配钞');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5009', '清分机查询', '/channel-center/v2/dev/count', 'GET', 2, '钞处中心—清分机信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5010', '清分机新增', '/channel-center/v2/dev/count', 'POST', 2, '钞处中心—清分机信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5011', '清分机修改', '/channel-center/v2/dev/count', 'PUT', 2, '钞处中心—清分机信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5012', '清分机删除', '/channel-center/v2/dev/count/*', 'DELETE', 2, '钞处中心—清分机信息管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5013', '清点计划分页查询', '/clear-center/v2/taskToCount/qryCountTaskList', 'GET', 2, '钞处中心—计划配钞清点');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5014', '计划清点', '/clear-center/v2/taskToCount/countTask', 'POST', 2, '钞处中心—计划配钞清点');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5015', '清点设备计划分配', '/clear-center/v2/taskToCount/qryCountDevList', 'GET', 2, '钞处中心—计划配钞清点');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5016', '配钞计划分页查询', '/clear-center/v2/taskToCount/qryCountTaskList', 'GET', 2, '钞处中心—计划配钞清点');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5017', '计划配钞', '/clear-center/v2/taskToCount/countTask', 'POST', 2, '钞处中心—计划配钞清点');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5018', '配钞设备计划分配', '/clear-center/v2/taskToCount/qryCountDevList', 'GET', 2, '钞处中心—计划配钞清点');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5101', '备钞入库', '/storage/v2/goodFlow/in', 'POST', 3, '仓储中心：实物入库');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5102', '尾箱出库', '/storage/v2/goodFlow/out', 'POST', 3, '仓储中心：实物出库');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5103', '获取笼车对应的仓储实物信息', '/storage/v2/goodFlow/showStorageEntity', 'GET', 3, '仓储中心：获取笼车对应的仓储实物信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5104', '库存查询', '/storage/v2/goodManage', 'GET', 3, '仓储中心：库存查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5105', '查看库存详情', '/storage/v2/goodManage/detail', 'GET', 3, '仓储中心：查看库存详情');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5106', '查询当天可执行的线路', '/storage/v2/goodFlow/qryTodayLine', 'GET', 3, '仓储中心：查看库存详情');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5111', '入库(工作流)', '/storage/v2/goodIn/in', 'POST', 3, '仓储中心：实物入库');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5112', '出库(工作流)', '/storage/v2/goodOut/out', 'POST', 3, '仓储中心：实物出库');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5113', '入库--带车（工作流）', '/storage/v2/goodIn/inWithShelf', 'POST', 3, '仓储中心：实物入库');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5114', '出库--解绑笼车(工作流)', '/storage/v2/goodOut/outUntied', 'POST', 3, '仓储中心：实物出库');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5115', '现金调入', '/storage/v2/goodFlow/inner/in', 'POST', 2, '仓储中心：现金调入');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5116', '现金调出', '/storage/v2/goodFlow/inner/out', 'POST', 2, '仓储中心：现金调出');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5117', '分页查询调入调出记录', '/storage/v2/goodFlow/inner/record', 'GET', 3, '仓储中心：分页查询调入调出记录');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5118', '查询调入调出详情', '/storage/v2/goodFlow/inner/detail', 'GET', 3, '仓储中心：查询调入调出详情');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5119', '现金库存查询', '/storage/v2/goodManage/storageEntity', 'GET', 2, '仓储中心：现金库存查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5201', '交易记录分页查询', '/accounts-administration/v2/biztxlog', 'GET', 2, '账务管理：交易记录查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5202', '核库', '/accounts-administration/v2/check', 'POST', 2, '账务管理：核库');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('5203', '分页查询核库信息', '/accounts-administration/v2/check', 'GET', 2, '账务管理：分页查询核库信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6001', '锁具列表查询', '/lock/v2/lock', 'GET', 2, '电子密码锁模块：锁具列表查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6002', '锁具信息详细查询', '/lock/v2/lock/detail', 'GET', 2, '电子密码锁模块：锁具信息详细查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6003', '锁具信息增加', '/lock/v2/lock', 'POST', 2, '电子密码锁模块：锁具信息增加');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6004', '锁具信息修改', '/lock/v2/lock', 'PUT', 2, '电子密码锁模块：锁具信息修改');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6005', '锁具信息删除', '/lock/v2/lock/*', 'DELETE', 2, '电子密码锁模块：锁具信息删除');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6006', '锁具日志查询', '/lock/v2/lockTrans', 'GET', 2, '电子密码锁模块：锁具日志查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6007', '锁具状态监控', '/lock/v2/lockStatus', 'GET', 2, '电子密码锁模块：锁具状态监控');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6101', '网点调拨申请', '/business/v2/transfer', 'POST', 2, '业务模块：网点调拨管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6102', '网点调拨修改', '/business/v2/transfer', 'PUT', 2, '业务模块：网点调拨管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6103', '网点调拨审核', '/business/v2/transfer/audit', 'PUT', 2, '业务模块：网点调拨管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6104', '网点调拨删除', '/business/v2/transfer/delTask/*', 'DELETE', 2, '业务模块：网点调拨管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6201', '网点领现申请', '/business/v2/receipt', 'POST', 2, '业务模块：网点领现管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6301', '安防预警信息查询', '/security-center/v2/securityWarn', 'GET', 2, '安防中心：预警信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6302', '安防预警信息发送', '/security-center/v2/securityWarnSend', 'POST', 2, '安防中心：预警信息发送');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6401', '现金调拨申请', '/business/v2/cashTransfer', 'POST', 2, '业务模块：现金调拨管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6402', '现金调拨修改', '/business/v2/cashTransfer', 'PUT', 2, '业务模块：现金调拨管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6501', '上门预约分页查询', '/business/v2/visitOrder', 'GET', 2, '业务模块：上门预约管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6502', '上门预约修改', '/business/v2/visitOrder', 'PUT', 2, '业务模块：上门预约管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6503', '上门预约添加', '/business/v2/visitOrder', 'POST', 2, '业务模块：上门预约管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6504', '上门预约删除', '/business/v2/visitOrder', 'DELETE', 2, '业务模块：上门预约管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6505', '查询上门客户信息列表', '/business/v2/visitOrder/orderCustomers', 'GET', 2, '业务模块：上门预约管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6506', '上门预约审核', '/business/v2/visitOrder/audit', 'PUT', 2, '业务模块：上门预约管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6601', '节假日线路规划', '/channel-center/v2/HolidayLineRunMap', 'PUT', 2, '渠道中心—节假日线路管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6901', '查询个性可用产品', '/business/v2/selfProduct/productForUse', 'GET', 2, '业务模块：自定义业务管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6902', '查询个性可用产品详情', '/business/v2/selfProduct/detail/*', 'GET', 2, '业务模块：自定义业务管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('6903', '个性产品申请订单', '/business/v2/selfProduct', 'POST', 2, '业务模块：自定义业务管理');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7001', '分页查询产品信息', '/product-center/v2/productInfo', 'GET', 3, '产品中心：产品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7002', '查询产品信息详情', '/product-center/v2/productInfo/*', 'GET', 3, '产品中心：产品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7003', '查询服务对象类型', '/product-center/v2/productInfo/customer', 'GET', 3, '产品中心：产品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7004', '增加商品信息', '/product-center/v2/goodsBase', 'POST', 2, '产品中心：商品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7005', '查询商品基础信息', '/product-center/v2/goodsBase', 'GET', 2, '产品中心：商品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7006', '修改商品信息', '/product-center/v2/goodsBase', 'PUT', 2, '产品中心：商品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7007', '删除商品信息', '/product-center/v2/goodsBase/*', 'DELETE', 2, '产品中心：商品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7008', '商品基础信息详情查询', '/product-center/v2/goodsBase/detail', 'GET', 2, '产品中心：商品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7009', '增加商品属性信息', '/product-center/v2/goodsProperty', 'POST', 2, '产品中心：商品属性');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7010', '商品所属信息查询', '/product-center/v2/goodsBase/upperInfoList', 'GET', 2, '产品中心：商品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7011', '增加产品信息', '/product-center/v2/productInfo', 'POST', 3, '产品中心：产品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7012', '修改产品状态', '/product-center/v2/productInfo/status', 'PUT', 3, '产品中心：产品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7013', '更新产品节点状态', '/product-center/v2/productInfo/status', 'POST', 3, '产品中心：产品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7014', '查询模块信息', '/product-center/v2/productInfo/modules', 'GET', 3, '产品中心：产品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7015', '更新产品节点顺序', '/product-center/v2/productInfo/statusConvert', 'POST', 3, '产品中心：产品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7016', '更新产品物品信息', '/product-center/v2/productInfo/goods', 'POST', 3, '产品中心：产品基础信息');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('7017', '商品属性值查询', '/product-center/v2/goodsProperty/propertyValue', 'GET', 2, '产品中心：商品属性');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('9001', '定时任务查询', '/time-job/v2/job', 'GET', 2, '定时任务模块：定时任务查询');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('9002', '定时任务增加', '/time-job/v2/job', 'POST', 2, '定时任务模块：定时任务增加');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('9003', '定时任务修改', '/time-job/v2/job', 'PUT', 2, '定时任务模块：定时任务修改');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('9004', '定时任务删除', '/time-job/v2/job/*', 'DELETE', 2, '定时任务模块：定时任务删除');

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('9005', '定时任务日志查询', '/time-job/v2/jobLog', 'GET', 2, '定时任务模块：定时任务日志查询');

commit;
