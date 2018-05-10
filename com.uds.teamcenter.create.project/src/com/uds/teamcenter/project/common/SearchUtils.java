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
		//��ȡ�ؼ���
		Map<String,String> cons = new HashMap<String,String>();
		for(Map.Entry<SearchCondition, String> opt:conditions.entrySet()){
			SearchCondition key = opt.getKey();
			String val = opt.getValue();
			String entry = GetSearchEntryId(key);
			if(entry != null){
				cons.put(entry, val);
			}
		}
		//��ѯ����			
		String QUERY_CLASS = searcherName;
		//��ȡ��ѯ type
		TCComponentQueryType typeComponent = (TCComponentQueryType) session.getTypeComponent("ImanQuery");
		TCComponentQuery query = null;
		// ���ݲ�ѯ���ƻ�ȡ����ز�ѯ
		TCComponent queryComponent = typeComponent.find(QUERY_CLASS);
		if (queryComponent == null) {
			throw new TCException("δ�ҵ���ѯ"+QUERY_CLASS+"������ϵ����Ա����!");
		}
		query = (TCComponentQuery) queryComponent;	
		
		//������ѯ����
		TCQueryClause[] queryClause = query.describe();
		Map<String,String> searchCons = TcUtils.BuildSearchCondition(queryClause, cons);
		if(searchCons.size() > 0){
			Set<String> keySet = searchCons.keySet();
			String[] proNames = keySet.toArray(new String[keySet.size()]);
			Collection<String> valSet = searchCons.values();
			String[] values = valSet.toArray(new String[valSet.size()]);
			
			// ��ȡ��ѯ���
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
	//��ȡ��ѯ��
	public static TCComponentQuery getTCComponentQuery(String query_class,TCSession session) throws TCException {
		
		String QUERY_CLASS = query_class;
		TCComponentQueryType typeComponent = (TCComponentQueryType) session.getTypeComponent("ImanQuery");
		TCComponentQuery query = null;
		TCComponent queryComponent = typeComponent.find(QUERY_CLASS);
		if (queryComponent == null) {
			throw new TCException("δ�ҵ���ѯ" + QUERY_CLASS + "������ϵ����Ա����!");
		}
		query = (TCComponentQuery) queryComponent;
		return query;
	}
	
	
	/**
	 * @param query
	 * @param values  ������������
	 * @param values  ����ֵ����
	 * @return	���ص��ǲ�ѯ�õ��Ľ������
	 * @throws TCException
	 */
	public static TCComponent[] getSearchResult(TCComponentQuery query,String[] propertyName,String[] values) throws TCException{
	
		TCComponent[] results = query.execute(propertyName, values);
		return results;
	}
	
	
}
