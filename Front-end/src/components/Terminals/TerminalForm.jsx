import React, { useState } from 'react';
import { Button, Card, CardContent, TextField } from "@mui/material";
import { v4 as uuidv4 } from 'uuid';

export default function TerminalForm({ onSubmit }) {
    const [ssid, setSsid] = useState("");
    const [password, setPassword] = useState("");

    const handleSsidChange = (event) => {
        setSsid(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        onSubmit({ ssid, password });
        setSsid("");
        setPassword("");
    };

    return (
        <Card>
            <CardContent>
                <form onSubmit={handleSubmit}>
                    <TextField
                        required
                        label="SSID"
                        variant="outlined"
                        value={ssid}
                        onChange={handleSsidChange}
                    />
                    <TextField
                        required
                        label="Password"
                        variant="outlined"
                        value={password}
                        onChange={handlePasswordChange}
                    />
                    <Button type="submit" variant="contained">Add Terminal</Button>
                </form>
            </CardContent>
        </Card>
    );
}
