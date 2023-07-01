import React, { useEffect, useState,useContext } from 'react';
import Sidebar from "../../components/Sidebar";
import { Box, Grid, Card, CardContent, Typography, IconButton } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import { useConfirm } from 'material-ui-confirm';
import { fetchBusinessTerminals, postNewTerminal, deleteTerminal } from "../../services/apiService";
import TerminalForm from '../../components/Terminals/TerminalForm';
import UserContext from '../../context/user-context';

function TerminalsPage() {
    const [terminals, setTerminals] = useState([]);
    const confirm = useConfirm();
    const { businessId } = useContext(UserContext);


    useEffect(() => {
        fetchBusinessTerminals(businessId).then(fetchedTerminals => {
            setTerminals(fetchedTerminals);
        });
    }, []);

    const handleAddTerminal = async ({ ssid, password }) => {
        const newTerminal = {
            business_id: businessId,
            is_contactless_enabled: "true"
        };

        const createdTerminal = await postNewTerminal(newTerminal);

        // Download the JSON file
        const jsonData = {
            ssid: ssid,
            password: password,
            mqtt_server: "56c1cf56ffc44989b69b25827d6064e7.s2.eu.hivemq.cloud",
            mqtt_port: 8883,
            mqtt_user: "test_user",
            mqtt_password: "TESTpassword123",
            mqtt_topic: "nfc/scan",
            uuid: createdTerminal.uuid
        };
        const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(jsonData));
        const downloadAnchorNode = document.createElement('a');
        downloadAnchorNode.setAttribute("href",     dataStr);
        downloadAnchorNode.setAttribute("download", "terminal_config.json");
        document.body.appendChild(downloadAnchorNode); // required for firefox
        downloadAnchorNode.click();
        downloadAnchorNode.remove();

        // Update the state with the new terminal
        setTerminals(prev => [...prev, createdTerminal]);
    };

    const handleDeleteTerminal = (terminalId) => {
        confirm({
            description: `Are you sure you want to delete terminal with ID ${terminalId}?`
        }).then(() => {
            deleteTerminal(businessId,terminalId).then(() => {
                setTerminals(terminals.filter(t => t.id !== terminalId));
            }).catch(() => {
                // handle error
            });
        }).catch(() => {
            // user clicked "Cancel"
        });
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <Sidebar/>
            <Box component="main" sx={{ flexGrow: 1, ml: { sm: 35 }, m: 3, width: '100%' }}>
                <Grid container spacing={3}>
                    {terminals.map((terminal, index) => (
                        <Grid item xs={12} md={6} lg={4} key={index}>
                            <Card>
                                <CardContent>
                                    <Typography variant="h5" component="div">{terminal.id}</Typography>
                                    <Typography variant="body2" color="text.secondary">{terminal.uuid}</Typography>
                                    <IconButton edge="end" aria-label="delete" onClick={() => handleDeleteTerminal(terminal.id)}>
                                        <DeleteIcon />
                                    </IconButton>
                                </CardContent>
                            </Card>
                        </Grid>
                    ))}
                    <Grid item xs={12} md={6} lg={4}>
                        <TerminalForm onSubmit={handleAddTerminal} />
                    </Grid>
                </Grid>
            </Box>
        </Box>
    );
}

export default TerminalsPage;
