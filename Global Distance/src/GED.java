import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class GED {
	static FileReader train;
	static FileReader dictionary;
	static List<String> queryWords = new ArrayList<String>();
	static List<String> answers = new ArrayList<String>();
	static List<String> entries = new ArrayList<String>(); 
	public static void main(String[] args) {		
		try {
			train = new FileReader("C:/Users/hp/Desktop/Knowledge Technology/2017S1-90049P1-data-dos/train.txt");
			dictionary = new FileReader("C:/Users/hp/Desktop/Knowledge Technology/2017S1-90049P1-data-dos/names.txt");
			BufferedReader reader = new BufferedReader(train);
			BufferedReader dicReader = new BufferedReader(dictionary);
			
			File resultList = new File("result4.txt");
			FileWriter filewriter = new FileWriter(resultList.getName());
			BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
			FileReader resultfile = new FileReader("result4.txt");
			BufferedReader read_result = new BufferedReader(resultfile);
			
			String row = null;
			String[] getSplit = null;
			String queryWord = null;
			String answer = null;
			String entry = null;
			while((row = reader.readLine())!=null){
				getSplit = row.split("\t");
				queryWord = getSplit[0];
				queryWord = queryWord.toLowerCase();
				answer = getSplit[1];
				queryWords.add(queryWord);
				answers.add(answer);
			}
			while((row = dicReader.readLine())!=null){
				entries.add(row);
			}
			int count = 0;
			for(String word:queryWords){
				int bestScore = -100;
				String result = null;
				for(String entryWord:entries){
					int temp_score = GED_Soundex(word, entryWord);
					if(temp_score>bestScore){
						bestScore = temp_score;
						result = entryWord;
					}
				}
				bufferedwriter.write(result+"\n");	
				System.out.println("Write "+count+" times.");
				count++;
			}
			bufferedwriter.close();
			/*calculate the correct rate*/
			String expect_result;
			int correctNum = 0;
			int total = 13437;
			double correctRate = 0;
			List result_list = new ArrayList<>();
			while((expect_result=read_result.readLine())!=null){
				result_list.add(expect_result);
			}
			for(int i=0;i<result_list.size();i++){
				if(result_list.get(i).equals(answers.get(i))){
					correctNum = correctNum+1;
				}
			}
			correctRate = (double)correctNum/total;
			DecimalFormat df = new DecimalFormat("0.00%");
			System.out.println("correct: "+correctNum);
			System.out.println("total: "+total);
			System.out.println(df.format(correctRate));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static int Max(int a, int b, int c){
		int max = a;
		if (max<b)
	        max=b;
	    if (max<c)
	        max=c;
	    return max;
	}

	public static int getReplaceScore(String q, String e){
		int score = 0;
		if(q.equals(e)){
			score = 2;//1
		}
		else{
			if(q.equals("a")){
				switch(e){
				case "y":
					score = 0;
					break;
				case "e":
					score = 0;//1
					break;
				case "i":
					score = -1;
					break;
				case "o":
					score = -1;
					break;
				case "u":
					score = -1;
					break;
				default:
					score = -2;
					break;
				}
			}
			else if(q.equals("e")){
				switch(e){
				case "a":
					score = 0;//1
					break;
				case "i":
					score = -1;
					break;
				case "o":
					score = -1;
					break;
				case "u":
					score = -1;
					break;
				default:
					score = -2;
					break;
				}
			}
			else if(q.equals("i")){
				switch(e){
				case "a":
					score = -1;
					break;
				case "e":
					score = -1;
					break;
				case "o":
					score = -1;
					break;
				case "u":
					score = -1;
					break;
				default:
					score = -2;
					break;
				}
			}
			else if(q.equals("o")){
				switch(e){
				case "a":
					score = -1;
					break;
				case "e":
					score = -1;
					break;
				case "i":
					score = -1;
					break;
				case "u":
					score = -1;
					break;
				default:
					score = -2;
					break;
				}
			}
			else if(q.equals("u")){
				switch(e){
				case "a":
					score = -1;
					break;
				case "e":
					score = -1;
					break;
				case "i":
					score = -1;
					break;
				case "o":
					score = -1;
					break;
				case "v":
					score = 1;//0
					break;
				case "w":
					score = 1;//0
					break;
				default:
					score = -2;
					break;
				}
			}
			else if(q.equals("y")){
				switch(e){
				case "a":
					score = 0;
					break;
				default:
					score = -2;
					break;
				}
			}
			else if(q.equals("w")){
				switch(e){
				case "v":
					score = 1;//0
					break;
				case "u":
					score = 1;//0
					break;
				default:
					score = -2;
					break;
				}
			}
			else if(q.equals("v")){
				switch(e){
				case "w":
					score = 1;//0
					break;
				case "u":
					score = 1;//0
					break;
				default:
					score = -2;
					break;
				}
			}
			else if(q.equals("m")){
				switch(e){
				case "n":
					score = -1;
					break;
				default:
					score = -2; 
					break;
				}
			}
			else if(q.equals("n")){
				switch(e){
				case "m":
					score = -1;
					break;
				default:
					score = -2;
					break;
				}
			}
			else if(q.equals("d")){
				switch(e){
				case "t":
					score = -1;
					break;
				default:
					score = -2;
					break;
				}
			}
			else if(q.equals("t")){
				switch(e){
				case "d":
					score = -1;
					break;
				default:
					score = -2;
					break;
				}
			}
			else if(q.equals("b")){
				switch(e){
				case "p":
					score = -1;
					break;
				default:
					score = -2;
					break;
				}
			}
			else if(q.equals("p")){
				switch(e){
				case "b":
					score = -1;
					break;
				default:
					score = -2;
					break;
				}
			}
			else{
				score = -2;
			}
		}
		return score;
	}
	
	public static int GED_Soundex(String QueString, String DicString){
		int insert = -1;
		int delete = -1;
		int[][] scoreMatrix = new int[DicString.length()+1][QueString.length()+1];
		/*Initialize scoring matrix*/
		for(int i=0;i<=DicString.length();i++){
			scoreMatrix[i][0] = i*-1;
		}
		for(int j=0;j<=QueString.length();j++){
			scoreMatrix[0][j] = j*-1;
		}
		/*Calculate the score*/
		String[] Que = QueString.split("");
		String[] Dic = DicString.split("");
		for(int q=1;q<=Que.length;q++){
			for(int d=1;d<=Dic.length;d++){
				if(Que[q-1].equals(Dic[d-1])==false){
					int insertScore = scoreMatrix[d-1][q]+insert;
					int deleteScore = scoreMatrix[d][q-1]+delete;
					int replaceScore = scoreMatrix[d-1][q-1]+getReplaceScore(Que[q-1],Dic[d-1]);
					scoreMatrix[d][q] = Max(insertScore, deleteScore, replaceScore);
				}
				else if(Que[q-1].equals(Dic[d-1])){
					scoreMatrix[d][q] = scoreMatrix[d-1][q-1]+getReplaceScore(Que[q-1],Dic[d-1]);
				}
			}
		}
		/*for(int i=0;i<scoreMatrix.length;i++){
			for(int j=0;j<scoreMatrix[i].length;j++){
				System.out.print(scoreMatrix[i][j]);
			}
			System.out.println();
		}*/	
		return scoreMatrix[DicString.length()][QueString.length()];
	}
}
