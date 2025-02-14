import React, { useState, useEffect } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';
import axios from 'axios';
import { toast } from 'react-toastify';

function EditEmployeeModal({ show, handleClose, employee, onEmployeeUpdated }) {
  const [editedEmployee, setEditedEmployee] = useState({ name: '', position: '', email: '', salary: '' });

  useEffect(() => {
    if (employee) setEditedEmployee(employee);
  }, [employee]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditedEmployee({ ...editedEmployee, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`/api/employees/${editedEmployee.id}`, editedEmployee);
      toast.success('Employee updated successfully!');
      onEmployeeUpdated();
      handleClose();
    } catch (error) {
      toast.error('Failed to update employee');
      console.error('Error updating employee:', error);
    }
  };

  if (!editedEmployee) return null;

  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>Edit Employee</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Name</Form.Label>
            <Form.Control type="text" name="name" value={editedEmployee.name || ''} onChange={handleChange} required />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Position</Form.Label>
            <Form.Control type="text" name="position" value={editedEmployee.position || ''} onChange={handleChange} required />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Email</Form.Label>
            <Form.Control type="email" name="email" value={editedEmployee.email || ''} onChange={handleChange} required />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Salary</Form.Label>
            <Form.Control type="number" name="salary" value={editedEmployee.salary || ''} onChange={handleChange} required />
          </Form.Group>
          <Button variant="primary" type="submit" className="w-100">
            Save Changes
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
}

export default EditEmployeeModal;
