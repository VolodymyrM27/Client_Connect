import React from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { Button, Typography, IconButton } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import { useConfirm } from 'material-ui-confirm';

export default function RequirementsList({ requirements, onDelete }) {
    const confirm = useConfirm();

    const handleDeleteClick = (requirement) => {
        confirm({
            description: `Are you sure you want to delete ${requirement.requirement_name}?`
        }).then(() => {
            onDelete(requirement);
        }).catch(() => {
            // user clicked "Cancel"
        });
    };

    const columns = [
        { field: 'id', headerName: 'ID', width: 90 },
        { field: 'requirement_name', headerName: 'Requirement Name', flex: 1 },
        {
            field: 'delete',
            headerName: 'Delete',
            width: 100,
            renderCell: (params) => (
                <IconButton
                    edge="end"
                    aria-label="delete"
                    onClick={() => handleDeleteClick(params.row)}
                >
                    <DeleteIcon />
                </IconButton>
            ),
        },
    ];

    return (
        <>
            <Typography variant="h4" component="div" gutterBottom>
                Supported Requirements
            </Typography>
            <div style={{ height: 400, width: '100%' }}>
                <DataGrid rows={requirements} columns={columns} pageSize={5} />
            </div>
        </>
    );
}
