<!DOCTYPE html>
<html>

<head>
	<meta name="layout" content="ranking"/>
</head>

<body>
	<div class=ranking>
    <h3> University Rankings </h3>
    <p> The rankings used for calculating each professor's <i>'prestige'</i> score is derived from the university rankings provided by
     <a href="http://www.topuniversities.com/university-rankings/world-university-rankings/2012">Top Universities</a>.
     As you can see the table below, Massachusetts Institute of Technology (MIT) is ranked 1st over all. How the ranking is completed
     is beyond our scope, we merely used the ranking to return meaningful and interesting results.
    </p>
    <center><img src="../images/ranking.png"></center>


    <p> We calculated each professor's individual score in the following way. For each listed education provided under their 'education'
        column, <a href="http://www.cs.sfu.ca/people/faculty.html">SFU's faculty page</a>, we were able to match that school to the
        ranking provided by <a href="http://www.topuniversities.com/university-rankings/world-university-rankings/2012">Top Universities</a>.

        ie. Simon Fraser University is ranked 282 out of 852 schools.
        The highest possible score is 100, and the lowest is 0. The higher the score, the more prestigious an individual's education would be.


    </p>

    <p> For example: Greg Baker's results returned from our vertical search engine looks like the following:
    </p>
    <center><img src="../images/ex1.png"></center>
    <p> Greg Baker's education includes:
        <div class="list" style="margin-left: 2em;">
        <li>Simon Fraser University with a score of 282. </li>
        <li>Queen's University with a score of 175.</li>
        <li> Average school ranks: (282 + 175) / 2 = 228.5</li>
        <li> The inverse of Greg's prestige score is 1 - ( 228.5 / 852 ) =  0.731</li>
        </div>
    </p>

     <p> Another example: Thomas Shermer's results returned from our vertical search engine looks like the following:
         </p>
         <center><img src="../images/ex2.png"></center>
         <p> Thomas Shermer's education includes:
             <div class="list" style="margin-left: 2em;">
             <li>John Hopkins University with a score of 16. </li>
             <li>McGill University with a score of 18.</li>
             <li> Average school ranks: (16 + 18) / 2 = 17</li>
             <li> The inverse of Thomas's prestige score is 1 - ( 17 / 852 ) =  0.980</li>
             </div>
         </p>


    </div>
</body>

</html>