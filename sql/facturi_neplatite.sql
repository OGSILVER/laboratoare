SELECT f.id, c.nume, f.data_emiterii, f.total
FROM facturi f
LEFT JOIN plati p ON p.factura_id = f.id
JOIN clienti c ON c.id = f.client_id
WHERE p.id IS NULL;
