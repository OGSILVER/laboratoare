SELECT ct.id, c.nume AS client, cb.nume AS contabil, ct.data_start
FROM contracte ct
JOIN clienti c ON c.id = ct.client_id
JOIN contabili cb ON cb.id = ct.contabil_id
WHERE ct.activ = TRUE;
