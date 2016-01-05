package com.vrv.cems.service.local;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import com.sys.common.fastdfs.FastDFSClient;
import com.sys.common.fastdfs.FastDFSClientException;
import com.sys.common.util.UUIDUtils;
import com.sys.common.util.security.Base64Utils;
import com.vrv.cems.service.base.SystemConstants;
import com.vrv.cems.service.updownload.bean.UpFileBean;
import com.vrv.cems.service.updownload.bean.UpLoadFile;
import com.vrv.cems.service.updownload.factory.FastDFSClientFactory;
import com.vrv.cems.service.updownload.util.DirUtil;
import com.vrv.cems.service.updownload.util.FileUtil;
import com.vrv.cems.service.updownload.util.XmlOperator;

public class TestCopy {

	/*
	 * static String key=UUIDUtils.get32UUID(); static int
	 * flag=BaseEncryptDecryptUtils.rondom(); public static void main(String[]
	 * args) throws ConfigurationException, TException, SQLException,
	 * ParseException { // TODO 自动生成的方法存根 String jsonData =
	 * "{'devOnlyId':'2014416','rUser':'72019b56795b49fe8ce1cd0d3477efd7','user':'69019b56795b49fe8ce1cd0d3477efd8','ip':'192.168.0.10','mac':'00-00-01-02-03',"
	 * +
	 * "'jdata':[{'product':'产品（公安定制版，税务版，中小企业版，教育版）','productType':'vdp/print','sign':'签名','type':'DES/RAS/RC4','clientId':'72019b56795b49fe8ce1cd0d3477ef',"
	 * +
	 * "'regUserId':'72019b56795b49fe8ce1cd0d3477','loginAccount':'system','sessionType':'auto/device'}]}"
	 * ;
	 * 
	 * 
	 * String json =
	 * "{'devOnlyId':'a5019b56795b49fe8ce1cd0d3477efd6','rUser':'72019b56795b49fe8ce1cd0d3477efd7',"
	 * +
	 * "'user':'69019b56795b49fe8ce1cd0d3477efd8','ip':'192.168.0.10','mac':'00-00-01-02-03',"
	 * + "'jdata':[{'type':'policy_version','id':'','data':'2001'}," +
	 * "{'type':'client_version','id':'','data':'2001'}," +
	 * "{'type':'product_version','id':'','data':[{'osType':'WINDOS','productType':'VDP','version':'1.2'}]},"
	 * + "{'type':'upolicy_version','id':'','data':'72123'}]}";
	 * 
	 * // 将json字符串转换为客户端输入bean ClientIn commonIn =
	 * CommonUtil.json2ClientIn(jsonData, DeviceLoginInJdata.class); //
	 * 连接dataprocess服务 // CommonUtil.updateDB(inParam.getData()); // 获得jdata数据
	 * DeviceLoginInJdata deviceLoginInJdata = (DeviceLoginInJdata) commonIn
	 * .getJdata().get(0);
	 * 
	 * DeviceCache deviceCache =new DeviceCache(); CacheService.Client
	 * cacheClient = CacheClient.generateClient(); CacheClient.TTransportOpen();
	 * 
	 * 
	 * PolicyCache policy=new PolicyCache(); policy.setId("316464641616");
	 * Result result= cacheClient.savePolicy(ICacheService.SERVICE_CODE,
	 * ICacheService.SAVEPOLICY,policy);
	 * System.out.println("savePolicy result"+result
	 * .getCode()+result.getInfo());
	 * ReflectUtils.copyObjectProperties(deviceCache, commonIn,
	 * DeviceCache.class, ClientIn.class);
	 * ReflectUtils.copyObjectProperties(deviceCache, deviceLoginInJdata,
	 * DeviceCache.class, DeviceLoginInJdata.class);
	 * 
	 * System.out.println("deviceCacheDevOnlyId"+deviceCache.getDevOnlyId()+"userId"
	 * +
	 * deviceCache.getUserOnlyId()+"ip"+deviceCache.getIp()+"mac"+deviceCache.getMac
	 * ()+deviceCache.getClientId()); CacheClient.TTransportClose();
	 * 
	 * String uuid=UUIDUtils.getUUID();
	 * 
	 * System.out.println("uuid"+uuid+"uuid.length"+uuid.length());
	 * 
	 * 
	 * Client commonClient = CommonClient.generateClient();
	 * CommonClient.TTransportOpen(); //commonClient.getDataTC(maxCode, minCode,
	 * checkCode, isZip, data, sessionId, msgCode) List<MorphDynaBean>
	 * mbList=new ArrayList<MorphDynaBean>();
	 * 
	 * MorphDynaBean mb1 =new MorphDynaBean(); //MorphDynaBean mb2 = new
	 * MorphDynaBean();
	 * 
	 * mb1.set("type","type","policy_version"); //mb1.set("data", "1001");
	 * 
	 * ClientIn commonIn2 = CommonUtil.json2ClientIn(json, TypeDataList.class);
	 * List<TypeDataList> tdList=new ArrayList<TypeDataList>();
	 * tdList=commonIn2.getJdata(); List<ProductInfo> pordList2=new
	 * ArrayList<ProductInfo>(); for(TypeDataList tl:tdList) {
	 * if("policy_version".equals(tl.getType())) { String
	 * data=(String)tl.getData().get(0); System.out.println("data"+data); } else
	 * if("product_version".equals(tl.getType())) { pordList2=tl.getData();
	 * System.out.println(pordList2.get(0).getOsType()); } } List<ProductInfo>
	 * pordList=new ArrayList<ProductInfo>(); ProductInfo prod =new
	 * ProductInfo(); prod.setProductType("wind"); prod.setBaseVersion("1.2");
	 * prod.setCustomVersion("1.2.4"); pordList.add(prod); TypeDataList tl=new
	 * TypeDataList(); tl.setData(pordList); tl.setType("product_version");
	 * 
	 * 
	 * TypeData td1=new TypeData(); td1.setType("policy_version");
	 * td1.setData("1001");
	 * 
	 * List<Object> obList=new ArrayList<Object>(); obList.add(td1);
	 * obList.add(tl);
	 * 
	 * String mbd=JsonUtils.collection2Json(obList);
	 * System.out.println("obList"+mbd); String test=" x ";
	 * if(StringUtils.isNotBlank(test)) {
	 * System.out.println("test is not null"); }
	 * 
	 * String password= Base64Util .getBASE64(CommonUtil
	 * .generateKey(SystemConfig.SERVER_COMMUNICATE_ARITHMETIC_TYPE));
	 * 
	 * System.out.println("password"+password);
	 * 
	 * System.out.println(32&16); System.out.println(17&16);
	 * System.out.println(48&16); System.out.println(24&16);
	 * 
	 * Configuration config = new PropertiesConfiguration( new
	 * CommentedProperties() .getPropertyFromCemsDat("path.config.properties"));
	 * String configIP = config.getString("server.ip"); int configPort =
	 * config.getInt("server.port");
	 * 
	 * CacheService.Client cacheClient = CacheClient.generateClient();
	 * CacheClient.TTransportOpen();
	 * 
	 * String key="123445"; String
	 * value=cacheClient.queryPtp(ICacheService.SERVICE_CODE,
	 * ICacheService.QUERYPTP, key); System.out.println("value"+value);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * String devOnlyId7="bd7f4481017bb3ca84818d1f87fafbce"; DeviceOnlineCache
	 * deviceOnline
	 * =cacheClient.queryDeviceOnlineByDevOnlyId(ICacheService.SERVICE_CODE,
	 * ICacheService.QUERYDEVICEONLINEBYDEVONLYID, devOnlyId7);
	 * System.out.println
	 * ("deviceOnline.getDevOnlyId()"+deviceOnline.getDevOnlyId
	 * ()+"deviceOnline.getActiveTime()"+deviceOnline.getActiveTime());
	 * 
	 * String ip="192.168.120.185"; String
	 * devOnlyId9=cacheClient.queryIP2DevOnlyId(ICacheService.SERVICE_CODE,
	 * ICacheService.QUERYIP2DEVONLYID, ip);
	 * System.out.println("devOnlyId"+devOnlyId9); DeviceCache
	 * deviceCache=cacheClient.queryDeviceByIp(ICacheService.SERVICE_CODE,
	 * ICacheService.QUERYDEVICEBYIP, ip); DeviceCache
	 * deviceCache2=cacheClient.queryDeviceByDevOnlyId
	 * (ICacheService.SERVICE_CODE, ICacheService.QUERYDEVICEBYDEVONLYID,
	 * devOnlyId9); System.out.println("deviceCache"+deviceCache.toString());
	 * System.out.println("deviceCache2"+deviceCache2.toString());
	 * DeviceOnlineCache
	 * deviceOnlineCache=cacheClient.queryDeviceOnlineByIp(ICacheService
	 * .SERVICE_CODE, ICacheService.QUERYDEVICEONLINEBYIP, ip);
	 * System.out.println("deviceOnlineCache"+deviceOnlineCache.toString());
	 * 
	 * String devOnlyId8="5b4888677243e45f95803f378c31f3c6"; DeviceCache
	 * deviceCache2
	 * =cacheClient.queryDeviceByDevOnlyId(ICacheService.SERVICE_CODE,
	 * ICacheService.QUERYDEVICEBYDEVONLYID, devOnlyId8);
	 * System.out.println("deviceCache2.getDevOnlyId()"
	 * +deviceCache2.getDevOnlyId
	 * ()+"deviceCache2.getUserOnlyId()"+deviceCache2.getUserOnlyId());
	 * 
	 * ICacheService cacheClient =
	 * ServiceFactory.getService(ICacheService.class, 5000); Result result=new
	 * Result(); List<String> list1=new ArrayList<String>(); List<String>
	 * list2=new ArrayList<String>(); list2.add("a"); int
	 * bigSize=(list1.size()>list2.size())?list1.size():list2.size(); int
	 * smallSize=(list1.size()<list2.size())?list1.size():list2.size();
	 * System.out.println(bigSize+"..."+smallSize); String
	 * uuid="23452345234523452345234523452345"; String ptpResult=
	 * " [{'procId':'进程ID','mark':'进程标记：0-不可结束进程；1-可结束','procName':'进程名称','procUrl:'进程路径','cpuPer':'cpu百分比','memory':'内存'，'threadCount':'线程数'，'handleCount':'句柄数'，'desc':'描述'}]"
	 * ; //expiredTime=5;
	 * 
	 * 
	 * result = cacheClient.savePtp(ICacheService.SERVICE_CODE,
	 * ICacheService.SAVEPTP, uuid,ptpResult);
	 * System.out.println("第一步存储"+result.getCode()+":"+result.getInfo());
	 * 
	 * //String userOnlyId="77777777777777777777777777777777"; //String
	 * uuid2="3aca2bb0e9dc43848824278541bddc31";
	 * result=cacheClient.isExist(ICacheService.SERVICE_CODE,
	 * ICacheService.ISEXIST,ICacheService.PREFIX_PTPCACHE,uuid);
	 * System.out.println("第二步判断是否存在"+result.getCode()+":"+result.getInfo());
	 * //String uuid2="3aca2bb0e9dc43848824278541bddc31";
	 * ptpResult=cacheClient.queryPtp(ICacheService.SERVICE_CODE,
	 * ICacheService.QUERYPTP,uuid);
	 * System.out.println("第三步查询"+"ptpResult:"+ptpResult); ICacheService
	 * cacheClient = ServiceFactory.getService(ICacheService.class);
	 * 
	 * DeviceOnlineCache deviceOnlineCache =new
	 * DeviceOnlineCache("100861008610086100861008610086",
	 * "2015-06-08 12:30:00", "2015-06-08 12:30:00", "", "2015-06-08 12:30:00",
	 * "192.168.120.1", "18080");
	 * 
	 * Result
	 * ip2devResult=cacheClient.saveIP2DevOnlyId(ICacheService.SERVICE_CODE,
	 * ICacheService.SAVEIP2DEVONLYID, "192.168.120.125",
	 * "100861008610086100861008610086");
	 * System.out.println(ip2devResult.toString());
	 * 
	 * // result= cacheClient.saveDeviceOnline(ICacheService.SERVICE_CODE,
	 * ICacheService.SAVEDEVICEONLINE,deviceOnlineCache); //
	 * System.out.println("存储 模拟数据到 设备在线缓存表 "+result.getInfo());
	 * 
	 * //CacheClient.TTransportClose();
	 * 
	 * result=cacheClient.isExist(ICacheService.SERVICE_CODE,
	 * ICacheService.ISEXIST,ICacheService.PREFIX_PTPCACHE,uuid);
	 * System.out.println("第五步再次判断是否存在"+result.getCode()+":"+result.getInfo());
	 * //PrintUtils.printBean(deviceCache); //CacheClient.TTransportClose();
	 * 
	 * String serverCode="00FF0700"; DBServParamBean
	 * servBean=DBUtil.queryByServiceCode(serverCode);
	 * System.out.println("servBean.getContent()"+servBean.getContent());
	 * 
	 * System.out.println("configIP"+configIP);
	 * System.out.println("configPort"+configPort); String
	 * userOnlyId="8b2438bb910ad972b7f4c5f64283b092"; String
	 * account=DBUtil.queryUserInfoById(userOnlyId);
	 * 
	 * System.out.println("account is:"+account);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * String sql="select * from " DBUtil.getColById(); }
	 * 
	 * 
	 * public static String getDayHourMinuteSecond(long timeMillis) {
	 * 
	 * 
	 * 
	 * long totalSeconds = timeMillis / 1000;
	 * 
	 * 
	 * 
	 * int second = (int) (totalSeconds % 60);// 秒
	 * 
	 * long totalMinutes = totalSeconds / 60;
	 * 
	 * int minute = (int) (totalMinutes % 60);// 分
	 * 
	 * long totalHours = totalMinutes / 60;
	 * 
	 * int hour = (int) (totalHours % 24);// 时
	 * 
	 * int totalDays = (int) (totalHours / 24);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * return totalDays + "天" + hour + "小时" + minute + "分" + second+"秒";
	 * 
	 * }
	 * 
	 * @Test public void test1() { System.out.println("key is:"+key);
	 * 
	 * System.out.println("flag is:"+flag); }
	 */
	/*
	 * @Test public void test2() { ClientHandler ch = null; try { ch =
	 * ConfigServerUtil.getConfigServiceClient(); } catch (Exception e) { //
	 * TODO 自动生成的 catch 块 e.printStackTrace(); } String areaId = "";
	 * List<ServiceConfigBean> serviceBeanList = null; try {
	 * 
	 * serviceBeanList = (List<ServiceConfigBean>) ch.invokeHandler(
	 * "loadServices", null); for (ServiceConfigBean servBean : serviceBeanList)
	 * { // if (servBean.serviceID.equals(IOnlineService.SERVICE_CODE)) { //
	 * areaId = servBean.getOrgID(); // }
	 * System.out.println("serv"+servBean.toString()); }
	 * 
	 * ReadConfigFileUtil.set(SystemConstants.PATH_CONFIG_PROPERTIES,
	 * "service.serverAreaId","11213123123132131313"); String serverArea=
	 * ReadConfigFileUtil.get(SystemConstants.PATH_CONFIG_PROPERTIES,
	 * "service.serverAreaId"); System.out.println("serverArea"+serverArea);
	 * 
	 * } catch (Exception e) { // TODO 自动生成的 catch 块 e.printStackTrace(); }
	 * PrintUtils.println(serviceBeanList.size()); for (ServiceConfigBean config
	 * : serviceBeanList) { PrintUtils.println(config); } }
	 * 
	 * @Test public void testRedis() { DeviceOnlineCache deviceOnlineCache =
	 * null; //ba6de08b6a75241fea73d6273f0bc3a7 String
	 * devOnlyId="f448a63acb90fd044322ffd23f07b39c"; String
	 * sessionId="121212212121212"; Date loginDate = new Date();
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 * String loginTime = sdf.format(loginDate); ICacheService
	 * iCacheService=ServiceFactory.getService(ICacheService.class, 5000); try
	 * {Map<String,String> deviceOnlineMap=new HashMap<String,String>();
	 * deviceOnlineMap.put("devOnlyId", devOnlyId);
	 * deviceOnlineMap.put("loginTime", loginTime);
	 * deviceOnlineMap.put("sessionId", sessionId);
	 * iCacheService.updateDeviceOnlineByField(ICacheService.SERVICE_CODE,
	 * ICacheService.UPDATEDEVICEONLINEBYFIELD, devOnlyId, deviceOnlineMap);
	 * 
	 * deviceOnlineCache=iCacheService.queryDeviceOnlineByDevOnlyId(ICacheService
	 * .SERVICE_CODE, ICacheService.QUERYDEVICEONLINEBYDEVONLYID, devOnlyId);
	 * 
	 * 
	 * } catch (TException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * System.out.println("deviceOnlineCache"+deviceOnlineCache.toString()); }
	 * 
	 * @Test public void testKey() { String type="E,C,A"; String
	 * encrypKey="F,B,C"; String devLogType=""; String[]
	 * encrypKeys=encrypKey.split(","); //String[] types=type.split(",");
	 * 
	 * for(int i=0;i<encrypKeys.length;i++) { if(type.contains(encrypKeys[i])) {
	 * devLogType=encrypKeys[i]; break; } }
	 * 
	 * System.out.println("devLogType"+devLogType); }
	 * 
	 * @Test public void testAES() { String str16="1234567890123456"; String
	 * str10="1234567890";
	 * 
	 * byte[] key="1234567890123456".getBytes(); byte[] iv=key;
	 * AESUtils.encrypt(str16.getBytes(), key, "AES/CBC/PKCS5Padding", iv);
	 * 
	 * System.out.println("AESUtils"+AESUtils.decrypt(str16.getBytes(),
	 * key).toString()); }
	 */
	/*
	 * @Test public void testHeartBeat() {
	 * 
	 * String testString = "["+ "{"+ "\"type\": \"policy_version\","+
	 * "\"data\": \"1001\""+ "},"+ "{"+ "\"type\": \"client_version\","+
	 * "\"data\": \"2001\""+ "},"+ "{"+ "\"type\": \"product_version\","+
	 * "\"data\": ["+ "{"+ "\"osType\": \"WINDOWS\","+
	 * "\"productType\": \"VDP\","+ "\"baseVersion\": \"1.2\","+
	 * "\"customVersion\": \"1.2.1\""+ "},"+ "{"+ "\"osType\": \"WINDOWS\","+
	 * "\"productType\": \"PRINT\","+ "\"baseVersion\": \"1.2\","+
	 * "\"customVersion\": \"1.2.1\""+ "},"+ "]"+ "},"+ "{"+
	 * "\"type\": \" upolicy_version\","+ "\"data\": ["+ "{"+
	 * "\"userOnlyId\": \"a5019b56795b49fe8ce1cd0d3477efd6\","+
	 * "\"version\": \"\""+ "},"+ "{"+
	 * "\"userOnlyId\": \"a5019b56795b49fe8ce1cd0d3477efd6\","+
	 * "\"version\": \"\""+ "}"+ "]"+ "}"+ "]";
	 * 
	 * 
	 * JSONArray jsonArray = JSONArray.fromObject(testString);
	 * for(Iterator<JSONObject> iterator =
	 * jsonArray.iterator();iterator.hasNext();){ JSONObject jsonObject =
	 * iterator.next();
	 * 
	 * String string = jsonObject.getString("type");
	 * if("policy_version".equals(string)) { TypeData policyData=(TypeData)
	 * JSONObject.toBean(jsonObject, TypeData.class);
	 * System.out.println("policyData"+policyData.toString());
	 * 
	 * } if("client_version".equals(string)) { TypeData clientData=(TypeData)
	 * JSONObject.toBean(jsonObject, TypeData.class);
	 * 
	 * 
	 * } if("product_version".equals(string)) { TypeDataList
	 * prodData=(TypeDataList) JSONObject.toBean(jsonObject,
	 * TypeDataList.class); List<ProductInfo> prodList=prodData.getData();
	 * 
	 * 
	 * }//upolicy_version if("upolicy_version".equals(string)) { TypeDataList
	 * upolicyData=(TypeDataList) JSONObject.toBean(jsonObject,
	 * TypeDataList.class); List<UserPolicyBean>
	 * upolicyList=upolicyData.getData();
	 * 
	 * }
	 * 
	 * } //List<HeartBean> heartBeanList =
	 * (List<HeartBean>)JSONArray.toCollection(jsonArray, HeartBean.class);
	 * //PrintUtils.printlnBeanCollection(heartBeanList); }
	 */

	/*
	 * @Test public void testTrim() { String str="abc bd e  f "; byte[]
	 * strBtye=str.getBytes();
	 * 
	 * int i=0; while(i<strBtye.length) {
	 * 
	 * if(strBtye[i]==32) { for(int j=i;j<strBtye.length;j++) {
	 * if(strBtye[j]!=32) { swap(); } } i++; } }
	 * 
	 * System.out.println(new String(strBtye)); }
	 */

	@Test
	public void testXMLOp() {
		// 1,设置一个对象 然后给对象属性赋值,写入xml文件
		// 2,设置另一个对象,然后 查找xml文件是否存在 若存在,则添加节点,并回写
		// 3,修改或者更新xml中的某个节点

		// 数据准备
		
		/*String dir = DirUtil.getAppDir()+ "/updownService";//"target/update/file.xml";
		String temp=dir+File.separator+"index.xml";
		File file = new File(temp);
		if (!dir.exists()){
			FileUtil.createDir(dir);
			
		}
		if(!file.exists()))
		{
			FileUtil
		}*/
		
		//建立索引文件
		/*String temp = DirUtil.getAppDir()+ "/updownService/index.xml";*/
		String temp="D:/test555.xml";
		File file = new File(temp);
		if(!file.exists())
		{
			FileUtil.createFile(temp);
		}
		
		 //初始化xmlop
		 XmlOperator.getInstance().setFile(file);
		 
		//String temp = DirUtil.getAppDir()+ "updownService/index.xml";//"target/classes/file.xml";
		/*
		 * FileUtil.createDir(temp); String
		 * xmlPath=System.getProperty("user.dir")+File.separator+temp;
		 */
		
		//建立UpLoadFile
		String id = UUIDUtils.get32UUID();
		String name = "test.zip";
		String url = "";
		String path = "x/y";
		String crc = "1A2B3C4D";
		String isUpFastFDS="0";
		UpLoadFile upfile = new UpLoadFile(id, name, crc, url, path,isUpFastFDS);
		try {
			XmlOperator.getInstance().addIndexXML(upfile);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		UpLoadFile upfile2 = new UpLoadFile("1231325", "next", "93849238",
				"www.baidu", "dd/uu","0");

		try {
			XmlOperator.getInstance().addIndexXML(upfile2);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
/*
	*//**
	 * @Title: addIndexXML
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param upfile2
	 * @param @param temp 参数说明
	 * @return void 返回类型
	 * @throws
	 *//*
	private static void updateIndexXML(UpLoadFile pat, String temp)
			throws Exception {
		Document doc = null;
		try {
			SAXReader sr = new SAXReader();// 获取读取xml的对象。
			doc = sr.read(temp);
			Element filesElement = doc.getRootElement();// 向外取数据，获取xml的根节点
			toFileNode(pat, filesElement);
		} catch (DocumentException e) {
			doc = DocumentHelper.createDocument();
			doc.setXMLEncoding("utf-8");
			Element root = doc.addElement("files");
			toFileNode(pat, root);
		} finally {
			writeXML(temp, doc);
		}

	}

	*//**
	 * @Title: writeXML
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param temp
	 * @param @param doc
	 * @param @throws FileNotFoundException
	 * @param @throws UnsupportedEncodingException
	 * @param @throws IOException 参数说明
	 * @return void 返回类型
	 * @throws
	 *//*
	public static void writeXML(String temp, Document doc)
			throws FileNotFoundException, UnsupportedEncodingException,
			IOException {
		FileOutputStream fos = new FileOutputStream(temp);
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
		XMLWriter writer = new XMLWriter(osw, outputFormat);
		writer.write(doc);
		writer.flush();
		writer.close();
	}

	*//**
	 * @Title: toFileNode
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param pat
	 * @param @param root 参数说明
	 * @return void 返回类型
	 * @throws
	 *//*
	public static void toFileNode(UpLoadFile pat, Element root) {
		Element ele = root.addElement("file");
		ele.addElement("id").addText(pat.getId());
		ele.addElement("name").addText(pat.getName());
		ele.addElement("crc").addText(pat.getCrc());
		ele.addElement("url").addText(pat.getUrl());
		ele.addElement("path").addText(pat.getPath());
	}

	public static void toIndexXML(UpLoadFile pat, String savePath)
			throws Exception {

		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("utf-8");
		Element root = document.addElement("files");
		toFileNode(pat, root);

		writeXML(savePath, document);
	}*/
	
	@Test
	public void testFastFDS() throws FastDFSClientException, FileNotFoundException
	{
		 FastDFSClient fastDFSClient = FastDFSClientFactory.createFastDFSClient();  
		 	String filePath="D:/test.txt";
		   File targetFile = new File(filePath);
		   String fdsUrl= fastDFSClient.uploadFile(targetFile);
		   System.out.println("fdsUrl:"+fdsUrl);
		   byte[] fileBytes= fastDFSClient.downloadFile(fdsUrl);
		   
			 String filePath2="D:/test222.txt";
			  OutputStream out = new FileOutputStream(filePath2, true); 
			  
			   try {  
		            byte[] buffer = new byte[1024];  
		           if(fileBytes.length>0)
		           {
		        	   out.write(fileBytes);
		           }
		        }  
		  
		        catch (MalformedURLException ex) {  
		        	ex.printStackTrace();
		        } catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
		        
	finally 
	{  
		 try {
			out.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
			  
	}
}
	
	/*@Test
	public void testOjbect2Xml()
	{
		String xmlPath="D:/index.xml";
		String xmlString = null;
		List<UpLoadFile> upList=new ArrayList<UpLoadFile>();
		xmlString = XmlOperator.xml2String(xmlPath);
		InputStream ins = null;
		try {
			ins =new ByteArrayInputStream(xmlString.getBytes());
			upList=XmlOperator.xmlToObject(xmlString, UpLoadFile.class,"file");
		}
		finally
		{
			try {
				ins.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
		for(UpLoadFile file:upList)
		{
			String url="g1/M00/FD/27/wKgAC1XsiOmAQiS5AAAAF-dvni4076.txt";
			System.out.println("file before:"+file.toString());
			String id=file.getId();
			file.setIsUpFastFDS("1");
			file.setUrl(url);
			XmlOperator.updateXMLbyId(id, file, xmlPath);
		}
		
	}
	*/
	@Test
	public void testBs64()
	{
		String data="CEMS-SERVICE-UPLOAD:192.168.0.133";
		String baseData=Base64Utils.encode2string(data);
		System.out.println("base64 encode"+baseData);
		System.out.println("base64 decode"+Base64Utils.decode2string(baseData));
	}
	
	@Test
	public void testSplit()
	{
		String appDir=DirUtil.getAppDir();
		String tempDir=DirUtil.getTempDir();
		System.out.println(6+6+"aa"+6+6);
			 
		 
	}
	
	@Test
	public void testFileReadLock()
	{
		   File file=new File("D:/test.txt");        
           
           //给该文件加锁    
           RandomAccessFile fis = null;
		try {
			fis = new RandomAccessFile(file, "rw");
		
           FileChannel fcin=fis.getChannel();    
           FileLock flin=null;  
           
           while(true){    
               try {  
                   flin = fcin.tryLock();  
                   break;  
               } catch (Exception e) {  
                    System.out.println("有其他线程正在操作该文件，当前线程休眠1000毫秒");   
					Thread.sleep(1000);				 
               }  
           }
           
           byte[] buf = new byte[1024];    
           StringBuffer sb=new StringBuffer();    
           while((fis.read(buf))!=-1){                    
               sb.append(new String(buf,"utf-8"));        
               buf = new byte[1024];    
           }    
               
           System.out.println(sb.toString());    
               
           flin.release();    
           fcin.close();    
           fis.close();    
           fis=null;    
	}
		catch(FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        } catch (InterruptedException e) {    
            e.printStackTrace();    
        }   
	}
	
	@Test
	public void testFileWriteLock()
	{
        File file=new File("D:/test333.txt");          
		try
		{
			 RandomAccessFile out = new RandomAccessFile(file, "rw");  
	            FileChannel fcout=out.getChannel();  
	            FileLock flout=null;  
	            while(true){    
	                try {  
	                    flout = fcout.tryLock();  
	                    break;  
	                } catch (Exception e) {  
	                     System.out.println("有其他线程正在操作该文件，当前线程休眠1000毫秒");   
	                     Thread.sleep(1000);    
	                }  
	                  
	            }  
	          
	            for(int i=1;i<=1000;i++){  
	            	Thread.sleep(10);  
	                StringBuffer sb=new StringBuffer();  
	                sb.append("这是第"+i+"行，应该没啥错哈 ");  
	                out.write(sb.toString().getBytes("utf-8"));  
	            }  
	              
	            flout.release();  
	            fcout.close();  
	            out.close();  
	            out=null;  
		}
		catch(FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        } catch (InterruptedException e) {    
            e.printStackTrace();    
        }   
	}
	
	@Test
	public void testMutiThread()
	{
		FileReadThread readers[] = new FileReadThread[5];  
        Thread threadsReader[] = new Thread[5];  
        for (int i = 0; i < 5; i++){  
            readers[i] = new FileReadThread();  
            threadsReader[i] = new Thread(readers[i]);  
        }  
        FileWriteThread writer = new FileWriteThread();  
        Thread threadWriter = new Thread(writer);  
        for (int i = 0; i < 5; i++){  
            threadsReader[i].start();  
        }  
        threadWriter.start();  
	}
}