AMI NAME : CS643FinalProjMovies
AMI ID: ami-70496115
Region: US East (Ohio)

#Cloud Project#
We tested 7 different test cases for Seasonal Movies, AvgRatings,Identifying Movie Genres, Top User influencers, Age groups based on gender, Zip codes for age groups

#Data Migration to HDFS#

After downloading the input file (states file), I followed 2 steps to transfer the contents from local machine to HDFS.

a.Copy this data from local to Namenode.
b.Copy this data from Namenode to hdfs.

Use the following commands:

Create a directory called states in Namenode and transfer the states content from local machine to Namenode/Akshay

Namenode$ mkdir ~/Akshay
local$ scp -i ~/.ssh/CS643proj-ami.pem ~/Downloads/Akshay/* Namenode:~/Akshay/
Namenode$ hdfs dfs -mkdir /Akshay
Namenode$ hdfs dfs -copyFromLocal ~/Akshay/* /Akshay/
Namenode$ hdfs dfs -mkdir /FinalResults

#AvgRating#
Change directory to AvgRating
Namenode$ cd AvgRating
Namenode$ source ~/.bash_profile

Compile the program

Namenode$ javac AvgRatingSorted.java -cp $(hadoop classpath)

Create a jar file called WordCounter.jar

Namenode$ jar cf AvgratingSorted.jar AvgRatingSorted*.class

Run the application program

Namenode$ hadoop jar AvgRatingSorted.jar AvgRatingSorted /Akshay/input/ratings.dat/ /FinalResults/out_avgratingsorted

Namenode$ hdfs dfs -cat /FinalResults/out_avgratingsorted/part-r-00000

#MovieGenreIdentifier#
Come to the directory Akshay
Namenode$ cd ..

Change directory to usermovierating
Namenode$ cd usermovierating
Namenode$ source ~/.bash_profile

Compile the program

Namenode$ javac MovieGenreIdentifier.java -cp $(hadoop classpath)

Create a jar file called MovieGenreIdentifier.jar

Namenode$ jar cf MovieGenreIdentifier.jar MovieGenreIdentifier*.class

Run the application program

Namenode$ hadoop jar MovieGenreIdentifier.jar MovieGenreIdentifier /Akshay/input/movies.dat /FinalResults/out_moviegenreidentifer "Father of the Bride Part II (1995)"

Namenode$ hdfs dfs -cat /FinalResults/out_moviegenreidentifer/part-r-00000

#UsersRatedNMovies#
Come to the directory Akshay
Namenode$ cd ..

Change directory to usermovierating
Namenode$ cd usermovierating
Namenode$ source ~/.bash_profile
Compile the program

Namenode$ javac UsersRatedNMovies.java -cp $(hadoop classpath)

Create a jar file called UsersRatedNMovies.jar

Namenode$ jar cf UsersRatedNMovies.jar UsersRatedNMovies*.class

Run the application program

Namenode$ hadoop jar UsersRatedNMovies.jar UsersRatedNMovies /Akshay/input/ratings.dat /Result/out_userratednmovies 1000

Namenode$ hdfs dfs -cat /FinalResults/out_userratednmovies/part-r-00000

#ZipCodeAvgAge#
Come to the directory Akshay
Namenode$ cd ..

Change directory to zipCodeAvgAge
Namenode$ cd zipCodeAvgAge

Compile the program

Namenode$ javac ZipCodeAvgAge.java -cp $(hadoop classpath)

Create a jar file called ZipCodeAvgAge.jar

Namenode$ jar cf ZipCodeAvgAge.jar ZipCodeAvgAge*.class

Run the application program

Namenode$ hadoop jar ZipCodeAvgAge.jar ZipCodeAvgAge /Akshay/input/users.dat/ /FinalResults/temp_ZipCodeAvgAge/ /FinalResults/out_ZipCodeAvg/

Namenode$ hdfs dfs -cat /FinalResults/out_ZipCodeAvg/part-r-00000

#GroupUserBasedOnGenderAndAge#
Come to the directory Akshay
Namenode$ cd ..

Change directory to group
Namenode$ cd group

Compile the program

Namenode$ javac GroupUserBasedOnGenderAndAge.java -cp $(hadoop classpath)

Create a jar file called GroupUserBasedOnGenderAndAge.jar

Namenode$ jar cf GroupUserBasedOnGenderAndAge.jar GroupUserBasedOnGenderAndAge*.class

Run the application program

Namenode$ hadoop jar GroupUserBasedOnGenderAndAge.jar GroupUserBasedOnGenderAndAge /Akshay/input/users.dat /FinalResults/out_group

Namenode$ hdfs dfs -cat /FinalResults/out_group/part-r-00000

#MovieIdentifierForGenre#
Come to the directory Akshay
Namenode$ cd ..

Change directory to movieByGenre
Namenode$ cd movieByGenre

Compile the program

Namenode$ javac MovieIdentifierForGenre.java -cp $(hadoop classpath)

Create a jar file called MovieIdentifierForGenre.jar

Namenode$ jar cf MovieIdentifierForGenre.jar MovieIdentifierForGenre*.class

Run the application program

Namenode$ hadoop jar MovieIdentifierForGenre.jar MovieIdentifierForGenre /Akshay/input/movies.dat /FinalResults/out_movieByGenre_thriller "Thriller"

Namenode$ hdfs dfs -cat /FinalResults/out_movieByGenre_thriller/part-r-00000

#SeasonalMovies#
Come to the directory Akshay
Namenode$ cd ..

Change directory to SeasonalMovies
Namenode$ cd SeasonalMovies

Compile the program

Namenode$ javac SeasonalMovies.java -cp $(hadoop classpath)

Create a jar file called SeasonalMovies.jar

Namenode$ jar cf SeasonalMovies.jar SeasonalMovies*.class

Run the application program

Namenode$ hadoop jar SeasonalMovies.jar SeasonalMovies /Akshay/input/movies_metadata.dat /FinalResults/out_SeasonalMovies

Namenode$ hdfs dfs -cat /FinalResults/out_SeasonalMovies/part-r-00000

Note : Pre Prep for any prior codes which are on HDFS which may prevent output
#WORDCOUNTER#
Namenode$ source ~/.profile
Namenode$ cd Filename
Namenode$ rm Filename*.class
Namenode$ rm Fielname.jar
Namenode$ hdfs dfs -rm -r path
