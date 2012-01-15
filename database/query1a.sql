SELECT occuDscr job,
       SUM(CASE WHEN pstOccuId IS NOT NULL THEN 1 ELSE 0 END) AS 'Posts',
       SUM(CASE WHEN usrOccuId IS NOT NULL THEN 1 ELSE 0 END) AS 'Users'
FROM  occupation_field
          LEFT OUTER JOIN
      users ON usrOccuId = occuId
          LEFT OUTER JOIN
      job_post ON pstOccuId = occuId
GROUP BY job
    HAVING
	(SUM(CASE WHEN pstOccuId IS NOT NULL THEN 1 ELSE 0 END) > 0
    OR
	SUM(CASE WHEN usrOccuId IS NOT NULL THEN 1 ELSE 0 END) > 0)