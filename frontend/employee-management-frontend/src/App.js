import React, { useState, useEffect } from 'react';
import EmployeeList from './components/EmployeeList';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const App = () => {
  const [employees, setEmployees] = useState([]);

  const fetchEmployees = async () => {
    try {
      const response = await axios.get('/api/employees');
      setEmployees(response.data);
    } catch (error) {
      console.error('Error fetching employees:', error);
    }
  };

  useEffect(() => {
    fetchEmployees();
  }, []);

  return (
    <div className="container mt-4">
      <h1 className="text-center mb-4">Employee Management System</h1>
      <EmployeeList employees={employees} onEmployeesChange={fetchEmployees} />
      <ToastContainer />
    </div>
  );
};

export default App;
