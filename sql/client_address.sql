SELECT c.nume, c.email, a.oras, a.strada
FROM clienti c
LEFT JOIN adrese a ON c.id = a.client_id;
