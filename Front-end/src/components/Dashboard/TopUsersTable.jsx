import React, { useState, useEffect } from 'react';
import { TableContainer, Paper, Table, TableHead, TableRow, TableCell, TableBody, Button, Modal, Box } from '@mui/material';
import { fetchUserProfile } from "../../services/apiService";
import {useTranslation} from "react-i18next";

export function TopUsersTable({ data }) {
    const { t, i18n } = useTranslation();

    const [open, setOpen] = useState(false);
    const [selectedUser, setSelectedUser] = useState(null);
    const [userData, setUserData] = useState(null);

    const handleOpen = (userId) => {
        fetchUserData(userId);
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const fetchUserData = async (userId) => {
        const response = await fetchUserProfile(userId);
        setUserData(response);
    };

    const modalStyle = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        boxShadow: 24,
        p: 4,
    };

    return (
        <div>
            <h2>{t("Most popular visitor")}</h2>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>{t("First Name")}</TableCell>
                            <TableCell>{t("Last Name")}</TableCell>
                            <TableCell>{t("Visits")}</TableCell>
                            <TableCell>{t("Action")}</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {data.map((user) => (
                            <TableRow key={user.user_id}>
                                <TableCell>{user.first_name}</TableCell>
                                <TableCell>{user.last_name}</TableCell>
                                <TableCell>{user.visits}</TableCell>
                                <TableCell>
                                    <Button variant="outlined" onClick={() => handleOpen(user.user_id)}>Подробнее</Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <Modal open={open} onClose={handleClose}>
                <Box sx={modalStyle}>
                    {userData && (
                        <div>
                            <h2>User Details</h2>
                            <p>First Name: {userData.first_name}</p>
                            <p>Last Name: {userData.last_name}</p>
                            <p>Date of Birth: {userData.date_of_birth}</p>
                            <p>Gender: {userData.gender}</p>
                            <p>Contact Number: {userData.contact_number}</p>
                            <p>Country: {userData.country}</p>
                            <p>State: {userData.state}</p>
                            <p>City: {userData.city}</p>
                        </div>
                    )}
                </Box>
            </Modal>

        </div>
    );
}
