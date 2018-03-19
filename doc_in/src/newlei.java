import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

class doc {
	String name;
	String text;
	int linecount =1,wordcount = 0,symbolcount=0,codeline=0,nullline = 0,balaline =666;
	boolean[] temp ={};
	public doc(){}
	public doc(String n,String t,boolean[] para){
		name = n;
		text = t;
		temp = para;
		cal();
	}
	public String  mes(){
		String message= "name:"+name+"\n";
		if(temp[2] ==true)
			message +="\tsymbolcount:"+symbolcount;
		
		if(temp[1] == true)
			message +="\twordcount:"+wordcount;
		
		if(temp[0]==true)
			message +="\tlinecount:"+linecount;
		if(temp[3] == true)
			message +="\tcodeline:"+codeline+"\tnullline:"+nullline+"\tbalaline:"+balaline;
		return message;
	}
	public static boolean isChinese(char c) {  
	    return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断  
	}  	
	public static boolean isword(char c) {  
	    return (c>='A'&&c<='Z')||(c>='a'&&c<='z')||(c>='0'&&c<='9');  
	}  
	public static boolean isnum(char c) {  
	    return (c>='0'&&c<='9');  
	}
	public static boolean issymbol(char c){
		return !(isChinese(c)||isword(c));
	}
	private void cal(){
		ArrayList linewords = new ArrayList();
		ArrayList<String> line = new ArrayList<String>();
		String ts ="";
		int lineb=0;
		boolean isaword = false;
			//多少行是指的多少回车吗
			//单词数 ，如果是汉语呢，那应该是一个字是一个词咯？
		for(int i =0;i<text.length();++i){
			char temp = text.charAt(i);
			ts+=temp;
			if(temp=='\n'){
				linewords.add(wordcount-lineb);
				lineb =wordcount;
				linecount +=1;
				line.add(ts);
				ts="";
			}
			else if(isword(temp)&&!isnum(temp)){
				isaword = true;
			}
			else if(isChinese(temp)){
				wordcount+=1;
				if(isaword)
					wordcount +=1;
				isaword = false;
			}
			else if(issymbol(temp))
			{
				if(isaword)
					wordcount +=1;
				isaword = false;
				if(issymbol(temp)){
					symbolcount +=1;
				}
			}
		}
		line.add(ts);
		linewords.add(wordcount-lineb);
		wordcount = isaword?wordcount+1:wordcount;
		
		/*for(int i=0;i<line.size();++i){			//solve the line problem
			System.out.println(line.get(i));
		}*/
		for(int i =0;i<linewords.size();i++){			//codeline,line and line you ***
			if((int)linewords.get(i)>1){
				codeline ++;
			}else if(isnullline(line.get(i)))
				nullline ++;
			else if(iszhushi(line.get(i))){
				balaline ++;
			}
				
		}
		
	}
	private boolean iszhushi(String string) {
		// TODO Auto-generated method stub
		return false;
	}
	private boolean isnullline(String str) {
		// TODO Auto-generated method stub
		boolean flag = false;
		for(int i=0;i<str.length();i++){
			if((!(str.charAt(i)==' '||str.charAt(i) =='\r'||str.charAt(i) =='\t'||str.charAt(i)=='\n'))&&!flag){
				flag = true;
			}else if((!(str.charAt(i)==' '||str.charAt(i) =='\r'||str.charAt(i) =='\t'||str.charAt(i)=='\n'))&&flag){
				return false;
			}
		}
		return true;
	}

}

class outdoc {
	String name;
	ArrayList children =new ArrayList();
	public void clear(){
		children.clear();
	}
}

public class newlei {
	/**
	 * @param args
	 */
	//final char[] para1= {'c','w','l'}; 
	public static String[]  TraversalDir(){
		File file = new File(".");
		/*try {
			System.out.println(file.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String[] fileList = file.list();		//get the filename in the dir
		ArrayList<String> returnlist =new ArrayList<String>();
		File[] fs = file.listFiles();
		for(int i =0;i<fileList.length;++i){
						//create the file handle in the dir by typing in one's name
			if(fs[i].isFile()){
				returnlist.add(fileList[i]);
				//System.out.println(fileList[i]);
			}
		}
		return (String[])returnlist.toArray(new String[0]);
	}
	public static String[]  TraversalDir(String suffix){
		File file = new File(".");
		try {
			System.out.println(file.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyFilter filter = new MyFilter(suffix);
		String[] fileList = file.list(filter);		//get the filename in the dir
		ArrayList<String> returnlist =new ArrayList<String>();
		File[] fs = file.listFiles(filter);
		for(int i =0;i<fileList.length;++i){
						//create the file handle in the dir by typing in one's name
			if(fs[i].isFile()){
				returnlist.add(fileList[i]);
				System.out.println(fileList[i]);
			}
			
		}
		return (String[])returnlist.toArray(new String[0]);
	}
	public static boolean createFile(String filePath){
		boolean result = false;
		File file = new File(filePath);
		if(!file.exists()){
			try{
				result = file.createNewFile();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		else System.out.println("file exists");
		return result;
	}
	public static String readFileByChars(String filePath){
		File file = new File(filePath);
		if(!file.exists()||!file.isFile()){
			return null;
		}
		StringBuffer content = new StringBuffer();
		try{
			char[] temp = new char[1];				//这tm成了逐个字符读入了
			InputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");  
			while(inputStreamReader.read(temp)!=-1){
				content.append(new String(temp));
				temp = new char[1];
			}
			fileInputStream.close();
			inputStreamReader.close();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		return content.toString();
	}
	public static void writeFileByChars(File file,String mes) throws IOException{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)); 
		bufferedWriter.write(mes);
		bufferedWriter.flush();// 清空缓冲区  
        bufferedWriter.close();// 关闭输出流  
	}
	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		boolean  isout = false;
		boolean[] para= {true,true,true,false};
		ArrayList parachar = new ArrayList();		//keep the load-in parameters in this arr
		ArrayList<doc> docarr = new ArrayList<doc>();	//keep the unoutput-to-file doc
		outdoc outdocarr = new outdoc();			//a pointer to the current outdoc
		for(int i =0;i<args.length;i++){
			if(args[i].charAt(0)=='-'){			//it's a parameter
				if(args[i].length()!=2)
					throw new Exception("a para after a -");
				if(args[i].charAt(1)=='o'){	//参数是-o
					isout= true;
					if(i-1<0)				//first param can't be -o
						throw new Exception("-o as the first para");
					if(i+1>=args.length)
						throw new Exception("-o as the last para");
					if(args[i+1].charAt(0)=='-'||args[i-1].charAt(0)=='-')//the para before 
															// or after must be filename
						throw new Exception("no filename after||before -o");
					if(docarr.size()==0)				//has to have at least one filename message
						throw new Exception("no file message to write!");
					outdoc tempo  =new outdoc();
					for(int index =0;index<docarr.size();index++){	//
						tempo.children.add(docarr.get(index));
					}
					docarr.clear();
					outdocarr = tempo;
				}
				else{						//参数不是-o
					parachar.add(args[i].charAt(1));
				}
			}
			else if(args[i].charAt(0)=='*'){		 		//**********************
				if(args[i].charAt(1)=='.'&&args[i].length()==2)
				{
					String[] filens = TraversalDir();
					doc tempd = new doc();
					for(int index = 0;index<filens.length;index++){
						if(parachar.size() ==0){
							 tempd= new doc(filens[index],readFileByChars(filens[index]),para);	
						}
						else{
							boolean[] x = getpara(parachar);		//get a boolean arr by judging the paraarr
							tempd= new doc(filens[index],readFileByChars(filens[index]),x);	//set an obj and clear the parachar	
						}	
						docarr.add(tempd);
						System.out.println(tempd.mes());		//print the result
					}
					parachar.clear();
						
				}
					
				//all files
				else {			//get the *.* file 	need to identify
					int len = args[i].length();
					String suffix = args[i].substring(1,len);
					String[] filens = TraversalDir(suffix);
					doc tempd = new doc();
					for(int index = 0;index<filens.length;index++){
						if(parachar.size() ==0){
							 tempd= new doc(filens[index],readFileByChars(filens[index]),para);	
						}
						else{
							boolean[] x = getpara(parachar);		//get a boolean arr by judging the paraarr
							tempd= new doc(filens[index],readFileByChars(filens[index]),x);	//set an obj and clear the parachar	
						}	
						docarr.add(tempd);
						System.out.println(tempd.mes());		//print the result
					}
					parachar.clear();
				}
			}
			else{				// a file input or output
				if(isout){
					outdocarr.name = args[i];		//change type?
					isout = false;	
					File file = new File(args[i]);
					if(!file.exists()){
						createFile(args[i])	;			//unsure,file points to the new created file?
					}
					String outputstr="";
					for(int index =0;index<outdocarr.children.size();index++)
						outputstr += ((doc) (outdocarr.children.get(index))).mes()+"\n";
					writeFileByChars(file,outputstr);										//write into the file
					outdocarr.clear();		//clear the outdocarr's children
				}
				else{
					doc tempd = new doc();
					if(parachar.size() ==0){
						 tempd= new doc(args[i],readFileByChars(args[i]),para);	
					}
					else{
						boolean[] x = getpara(parachar);		//get a boolean arr by judging the paraarr
						parachar.clear();
						tempd= new doc(args[i],readFileByChars(args[i]),x);	//set an obj and clear the parachar		
					}
					docarr.add(tempd);
					System.out.println(tempd.mes());		//print the result
			}	
		}
	}
}
	 static class MyFilter implements FilenameFilter{  
	        private String type;  
	        public MyFilter(String type){  
	            this.type = type;  
	        }  
	        public boolean accept(File dir,String name){  
	            return name.endsWith(type);  
	        }  
	    }  
	private static boolean[] getpara(ArrayList parachar) throws Exception{
		// TODO Auto-generated method stub
		boolean[] t ={false,false,false,false};
		for(int index=0;index<parachar.size();index++){
			switch ((char)(parachar.get(index))){
			case 'c':
				t[0] =true;
				break;
			case 'w':
				t[1]=true;
				break;
			case 'l':
				t[2] =true;
				break;
			case 'a':
				t[3] = true;
				break;
			default:
				throw new Exception("not exist such para");
			}
		}
		return t;
		}
	}
