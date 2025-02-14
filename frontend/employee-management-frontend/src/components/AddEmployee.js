import React, { useState } from 'react';
import { Button, Form, Modal } from 'react-bootstrap';
import axios from 'axios';
import { toast } from 'react-toastify';

function AddEmployee({ onEmployeeAdded }) {
  const [show, setShow] = useState(false);
  const [employee, setEmployee] = useState({ name: '', position: '', email: '', salary: '' });

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEmployee({ ...employee, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/api/employees', employee);
      toast.success('Employee added successfully!');
      onEmployeeAdded();
      handleClose();
    } catch (error) {
      toast.error('Failed to add employee');
      console.error('Error adding employee:', error);
    }
  };

  return (
    <>
      <Button variant="primary" onClick={handleShow}>
        Add Employee
      </Button>

      <Modal show={show} onHide={handleClose} centered>
        <Modal.Header closeButton>
          <Modal.Title>Add New Employee</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3">
              <Form.Label>Name</Form.Label>
              <Form.Control type="text" name="name" value={employee.name} onChange={handleChange} required />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Position</Form.Label>
              <Form.Control type="text" name="position" value={employee.position} onChange={handleChange} required />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control type="email" name="email" value={employee.email} onChange={handleChange} required />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Salary</Form.Label>
              <Form.Control type="number" name="salary" value={employee.salary} onChange={handleChange} required />
            </Form.Group>
            <Button variant="primary" type="submit" className="w-100">
              Add Employee
            </Button>
          </Form>
        </Modal.Body>
      </Modal>
    </>
  );
}

export default AddEmployee;
