SELECT occuDscr Description , IFNULL(Quantity,0) 
	FROM occupation_field 
		LEFT JOIN (SELECT occuId Id,COUNT(pstOccuId) Quantity 
FROM company,job_post 
	INNER JOIN occupation_field ON pstOccuId = occuId 
WHERE cmpId = pstCmpId AND cmpName = 'Ministry Of Finance' GROUP BY occuDscr) 
	second ON occuId = Id
