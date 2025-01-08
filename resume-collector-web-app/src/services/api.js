import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:3000", // Replace with the appropriate URL
});

export const getAllResumes = async () => {
  try {
    const response = await API.get("/resumes");
    return response.data;
  } catch (error) {
    console.error("Error fetching resumes:", error);
    throw error;
  }
};
