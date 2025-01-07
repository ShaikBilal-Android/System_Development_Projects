const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const { Pool } = require('pg');

// Initialize Express App
const app = express();
const port = 3000;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// PostgreSQL Database Connection
const pool = new Pool({
  user: 'postgres', // Replace with your PostgreSQL username
  host: 'localhost',        // Or your database host
  database: 'Resume_Collector_Database', // Replace with your PostgreSQL database name
  password: 'Apsac@6769', // Replace with your PostgreSQL password
  port: 5432,               // Default PostgreSQL port
});

// API Endpoints

// 1. Fetch All Resumes
app.get('/resumes', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM resumes');
    res.json(result.rows);
  } catch (err) {
    console.error('Error fetching resumes:', err);
    res.status(500).json({ error: 'Failed to fetch resumes' });
  }
});

// 2. Fetch Resume by ID
app.get('/resumes/:id', async (req, res) => {
  const { id } = req.params;
  try {
    const result = await pool.query('SELECT * FROM resumes WHERE id = $1', [id]);
    if (result.rows.length > 0) {
      res.json(result.rows[0]);
    } else {
      res.status(404).json({ error: 'Resume not found' });
    }
  } catch (err) {
    console.error('Error fetching resume:', err);
    res.status(500).json({ error: 'Failed to fetch resume' });
  }
});

// 3. Add a New Resume (Optional)
app.post('/resumes', async (req, res) => {
  const { full_name, email, phone, address, education, work_experience, skills, profile_picture_uri } = req.body;
  try {
    const query = `
      INSERT INTO resumes (full_name, email, phone, address, education, work_experience, skills, profile_picture_uri)
      VALUES ($1, $2, $3, $4, $5, $6, $7, $8) RETURNING *`;
    const values = [full_name, email, phone, address, education, work_experience, skills, profile_picture_uri];
    const result = await pool.query(query, values);
    res.status(201).json(result.rows[0]);
  } catch (err) {
    console.error('Error inserting resume:', err);
    res.status(500).json({ error: 'Failed to add resume' });
  }
});

// Start the Server
app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
