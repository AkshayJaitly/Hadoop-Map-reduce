import java.util.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.join.TupleWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Job;



public class SeasonalMovies {


	public static class MovieMapper extends Mapper<Object, Text, Text, IntWritable>{

		private final static IntWritable output = new IntWritable(1);

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			String itr = value.toString();
			int counter = 0;

			for (String curr_str : itr.split("\n")) {
				String[] str_arr = new String[10];
				str_arr = curr_str.split("\t");
				String genre_list = str_arr[1];
				String[] genre = genre_list.split("},");
				for(String str : genre){
					Pattern p = Pattern.compile("'([^\']*)'");
					Matcher m = p.matcher(str);
					while (m.find()) {
							if(!m.group(1).equals("id") && !m.group(1).equals("name")){

								// Get month info from movie release date
								String release_date;
								int month;
								int day;
								int start_index;
								try{
									release_date = str_arr[4];
									month = Integer.parseInt(release_date.substring(0, release_date.indexOf('/')));
									start_index = release_date.indexOf('/');
									day = Integer.parseInt(release_date.substring(start_index+1, release_date.indexOf('/', start_index+1)));
								}catch(ArrayIndexOutOfBoundsException e){
									System.out.println("Array index out of bounds at : "+ counter);
									continue;
								}catch(Exception e){
									continue;
								}

								// Halloween
								if(month == 10 && day > 20){
									context.write(new Text("Halloween" + "/" + m.group(1)), output);
								}

								// Christmas
								if(month == 12 && day > 20){
									context.write(new Text("Christmas" + "/" + m.group(1)), output);
								}

								// Valentines
								if(month == 2 && (day >= 9 && day <= 19)){
									context.write(new Text("Valentines" + "/" + m.group(1)), output);
								}

							}
					}
				}

			}
		}
	}

	public static class MovieReducer extends Reducer<Text,IntWritable,Text,IntWritable> {

		private IntWritable output = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> value, Context context) throws IOException, InterruptedException {
			int total_count = 0;
			for (IntWritable curr_value : value){
				total_count = total_count + curr_value.get();
			}
			output.set(total_count);
			context.write(key, output);
		}
	}




	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		Job job1 = Job.getInstance(conf, "Initial Genre Counts");
		job1.setJarByClass(SeasonalMovies.class);

		job1.setMapperClass(MovieMapper.class);
		job1.setCombinerClass(MovieReducer.class);
		job1.setReducerClass(MovieReducer.class);

		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job1, new Path(args[0]));
		FileOutputFormat.setOutputPath(job1, new Path(args[1]));

		job1.waitForCompletion(true);

	}

}
