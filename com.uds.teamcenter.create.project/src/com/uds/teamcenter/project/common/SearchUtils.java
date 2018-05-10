package com.uds.teamcenter.project.common;

import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentQuery;
import com.teamcenter.rac.kernel.TCComponentQueryType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;


public class SearchUtils {
	public static enum SearchCondition
	{
		ITEM_id,
		SCHEDULE_projectId,
		SCHEDULE_isTemplate
	};
	/*public static TCComponent[] GetSearchResult(TCSession session, String searcherName,Map<SearchCondition,String> conditions) throws Exception{
		//获取关键字
		Map<String,String> cons = new HashMap<String,String>();
		for(Map.Entry<SearchCondition, String> opt:conditions.entrySet()){
			SearchCondition key = opt.getKey();
			String val = opt.getValue();
			String entry = GetSearchEntryId(key);
			if(entry != null){
				cons.put(entry, val);
			}
		}
		//查询数据			
		String QUERY_CLASS = searcherName;
		//获取查询 type
		TCComponentQueryType typeComponent = (TCComponentQueryType) session.getTypeComponent("ImanQuery");
		TCComponentQuery query = null;
		// 根据查询名称获取到相关查询
		TCComponent queryComponent = typeComponent.find(QUERY_CLASS);
		if (queryComponent == null) {
			throw new TCException("未找到查询"+QUERY_CLASS+"，请联系管理员配置!");
		}
		query = (TCComponentQuery) queryComponent;	
		
		//构建查询条件
		TCQueryClause[] queryClause = query.describe();
		Map<String,String> searchCons = TcUtils.BuildSearchCondition(queryClause, cons);
		if(searchCons.size() > 0){
			Set<String> keySet = searchCons.keySet();
			String[] proNames = keySet.toArray(new String[keySet.size()]);
			Collection<String> valSet = searchCons.values();
			String[] values = valSet.toArray(new String[valSet.size()]);
			
			// 获取查询结果
			TCComponent[] results = query.execute(proNames, values);
			return results;
		}
	
		return null;
	}
	private static String GetSearchEntryId(SearchCondition con){
		if(con.equals(SearchCondition.SCHEDULE_projectId)){
			return "project_id";
		}else if(con.equals(SearchCondition.SCHEDULE_isTemplate)){
			return "IsScheduleTemplate";
		}else if(con.equals(SearchCondition.ITEM_id)){
			return "ItemID";
		}
		return null;
	}
	
	*/
	//获取查询器
	public static TCComponentQuery getTCComponentQuery(String query_class,TCSession session) throws TCException {
		
		String QUERY_CLASS = query_class;
		TCComponentQueryType typeComponent = (TCComponentQueryType) session.getTypeComponent("ImanQuery");
		TCComponentQuery query = null;
		TCComponent queryComponent = typeComponent.find(QUERY_CLASS);
		if (queryComponent == null) {
			throw new TCException("未找到查询" + QUERY_CLASS + "，请联系管理员配置!");
		}
		query = (TCComponentQuery) queryComponent;
		return query;
	}
	
	
	/**
	 * @param query
	 * @param values  属性名称数组
	 * @param values  属性值数组
	 * @return	返回的是查询得到的结果输租
	 * @throws TCException
	 */
	public static TCComponent[] getSearchResult(TCComponentQuery query,String[] propertyName,String[] values) throws TCException{
	
		TCComponent[] results = query.execute(propertyName, values);
		return results;
	}
	
	
}
