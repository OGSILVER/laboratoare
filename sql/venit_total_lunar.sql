SELECT DATE_TRUNC('month', data_emiterii) AS luna,
       SUM(total) AS venit_total
FROM facturi
GROUP BY luna
ORDER BY luna;
