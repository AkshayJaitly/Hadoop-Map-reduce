
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class MovieGenreIdentifier {



	public static class Map extends Mapper<LongWritable, Text, Text, IntWritable>
	{
		String movieName="";
		String inMovies="";
		private static final IntWritable one=new IntWritable(1);

		protected void setup(Context context)
		{
			Configuration config=context.getConfiguration();
			inMovies=config.get("inParameter");
		}


		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		{
			String line=value.toString();
			String[] currentMovieTuples=line.split("::");
			String[] inMovieList=inMovies.split(",");
			for(String s : inMovieList)
			{
				if(currentMovieTuples[1].trim().equalsIgnoreCase(s))
					context.write(new Text("Movie :: "+currentMovieTuples[1].trim()+" Genre :: "+currentMovieTuples[2].trim()), one);
			}

		}
	}

	public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable>
	{
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
		{
			for(IntWritable value : values)
				context.write(key, new IntWritable(0));
		}
	}


	/**
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		// TODO Auto-generated method stub

		Configuration conf = new Configuration();
	    conf.set("inParameter", toString(args));
	    Job job = new Job(conf, "MovieGenreIdentifier");

	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    job.setJarByClass(MovieGenreIdentifier.class);
	    job.setMapperClass(Map.class);
	    job.setCombinerClass(Reduce.class);
	    job.setReducerClass(Reduce.class);

	    job.setInputFormatClass(TextInputFormat.class);
	    job.setOutputFormatClass(TextOutputFormat.class);

	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));

	    job.waitForCompletion(true);
	}

	private static String toString(String[] list)
	{
		String ret="";
		for(int i=2;i<list.length;i++)
		{
			ret=ret+list[i]+" ";
		}
		return ret;
	}

}
