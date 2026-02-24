SELECT s.nume, COUNT(*) AS utilizari
FROM facturi_linii fl
JOIN servicii s ON s.id = fl.serviciu_id
GROUP BY s.id
ORDER BY utilizari DESC;
