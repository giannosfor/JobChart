SELECT
      oc.occuDscr job,
       IFNULL(postInfo.PostCount,0) Posts,
       IFNULL(userInfo.UserCount,0) Users
     FROM
      (SELECT * FROM occupation_field ORDER BY occuDscr) oc
       LEFT JOIN
       (
          SELECT occuDscr,COUNT(pstOccuId) AS 'PostCount'
            FROM job_post INNER JOIN occupation_field
            ON pstOccuId = OccuId GROUP BY occuDscr
        ) postInfo
        USING (occuDscr)
       LEFT JOIN
       (
            SELECT occuDscr,COUNT(usrOccuId) AS 'UserCount'
           FROM users INNER JOIN occupation_field
             ON usrOccuId = occuId GROUP BY occuDscr
      ) userInfo
      USING (occuDscr)
   WHERE
    IFNULL(postInfo.PostCount,0)>0 OR
    IFNULL(userInfo.UserCount,0)>0
 ;