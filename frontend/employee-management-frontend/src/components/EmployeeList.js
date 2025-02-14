import React, { useState } from 'react';
import { Table, Button, Form } from 'react-bootstrap';
import EditEmployeeModal from './EditEmployeeModal';
import AddEmployee from './AddEmployee';
import axios from 'axios';
import { toast } from 'react-toastify';

const EmployeeList = ({ employees, onEmployeesChange }) => {
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [showEditModal, setShowEditModal] = useState(false);
  const [filterPosition, setFilterPosition] = useState('All Positions');

  const handleEdit = (employee) => {
    setSelectedEmployee(employee);
    setShowEditModal(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this employee?')) {
      try {
        await axios.delete(`/api/employees/${id}`);
        toast.success('Employee deleted successfully!');
        onEmployeesChange();
      } catch (error) {
        toast.error('Failed to delete employee');
        console.error('Error deleting employee:', error);
      }
    }
  };

  const handleGenerateReport = async () => {
    try {
      const position = filterPosition === 'All Positions' ? '' : filterPosition;
      const response = await axios.get(`/api/employees/report?position=${position}`, { responseType: 'blob' });
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', `Employee_Report_${position || 'All'}.pdf`);
      document.body.appendChild(link);
      link.click();
      toast.success('Report generated successfully!');
    } catch (error) {
      toast.error('Failed to generate report');
      console.error('Error generating report:', error);
    }
  };

  const filteredEmployees = employees.filter((emp) =>
    filterPosition === 'All Positions' || emp.position === filterPosition
  );

  return (
    <div>
      <AddEmployee onEmployeeAdded={onEmployeesChange} />

      <Form.Select
        className="my-3"
        value={filterPosition}
        onChange={(e) => setFilterPosition(e.target.value)}
      >
        <option>All Positions</option>
        {[...new Set(employees.map((emp) => emp.position))].map((pos) => (
          <option key={pos}>{pos}</option>
        ))}
      </Form.Select>

      <Button variant="success" className="mb-3" onClick={handleGenerateReport}>
        Generate Report
      </Button>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Name</th>
            <th>Position</th>
            <th>Email</th>
            <th>Salary</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredEmployees.map((employee) => (
            <tr key={employee.id}>
              <td>{employee.name}</td>
              <td>{employee.position}</td>
              <td>{employee.email}</td>
              <td>{employee.salary}</td>
              <td>
                <Button variant="info" className="me-2" onClick={() => handleEdit(employee)}>
                  Edit
                </Button>
                <Button variant="danger" onClick={() => handleDelete(employee.id)}>
                  Delete
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      {showEditModal && (
        <EditEmployeeModal
          show={showEditModal}
          handleClose={() => setShowEditModal(false)}
          employee={selectedEmployee}
          onEmployeeUpdated={onEmployeesChange}
        />
      )}
    </div>
  );
};

export default EmployeeList;
