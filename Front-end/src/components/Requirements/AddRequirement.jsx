import React, { useState, useEffect } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, Select, MenuItem } from "@mui/material";
import { fetchRequirementsByCategory } from "../../services/apiService";

export default function AddRequirement({ onAdd, currentRequirements }) {
    const [open, setOpen] = useState(false);
    const [availableRequirements, setAvailableRequirements] = useState([]);
    const [selectedRequirement, setSelectedRequirement] = useState(null);

    useEffect(() => {
        fetchRequirementsByCategory(2).then(allRequirements => {
            const nonAddedRequirements = allRequirements.filter(req =>
                !currentRequirements.some(currentReq => currentReq.id === req.id)
            );
            setAvailableRequirements(nonAddedRequirements);
        });
    }, [currentRequirements]);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleAdd = () => {
        onAdd(selectedRequirement);
        setSelectedRequirement(null);
        handleClose();
    };

    const handleChange = (event) => {
        setSelectedRequirement(availableRequirements.find(req => req.id === event.target.value));
    };

    return (
        <div>
            <Button variant="contained" color="primary" onClick={handleClickOpen}>
                Add Requirement
            </Button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Add a New Requirement</DialogTitle>
                <DialogContent>
                    <Select
                        autoFocus
                        value={selectedRequirement ? selectedRequirement.id : ''}
                        onChange={handleChange}
                        fullWidth
                    >
                        {availableRequirements.map(req => (
                            <MenuItem key={req.id} value={req.id}>{req.requirement_name}</MenuItem>
                        ))}
                    </Select>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={handleAdd}>Add</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
