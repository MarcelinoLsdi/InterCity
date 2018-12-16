package com.example.InterCity.model;

import com.example.InterCity.service.MedService;
import util.CSVReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SendData {


    private static MedService service = new MedService();

    private static List<Resource> resources = new ArrayList<>();

    //public static void main(String[] args) {

      //  send();

    //}


    public static void send() {
        List<CapabilityDataAuxiliar> capabilityDataAuxiliars = new ArrayList<>();
        resources = service.buscarFarmaciaPeloMedicamento("formet");
        try {
            List<Context> contexts = lerInstanciasPollution("medicamentos2.csv");
            for (Resource resource : resources) {
                String list[] = {};
                resource.setCapabilities(list);
                for (Context context : contexts) {
                    CapabilityDataAuxiliar capabilityDataAuxiliar = new CapabilityDataAuxiliar();
                    capabilityDataAuxiliar.setResource(resource);
                    capabilityDataAuxiliar.setValue(context.getQtde());
                    capabilityDataAuxiliar.setLat(resource.getLat());
                    capabilityDataAuxiliar.setLon(resource.getLon());
                    capabilityDataAuxiliar.setDescription(context.getCapability());
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    capabilityDataAuxiliar.setTimestamp(dateFormat.format(date));

                    capabilityDataAuxiliars.add(capabilityDataAuxiliar);
                    Data data = new Data();
                    data.setData(capabilityDataAuxiliars);

                    service.getDataResource(data, resource.getUuid(), context.getCapability());
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<Context> lerInstanciasPollution(String arquivo) throws ParseException, FileNotFoundException {
        List<Context> Instances = new ArrayList<>();
        InputStream inputStream = new FileInputStream(arquivo);
        CSVReader cvsReader = new CSVReader(inputStream);
        List<String[]> linhas = cvsReader.read();
        for (String[] linha : linhas) {
            Context data = new Context();
            data.setCapability(linha[0]);
            data.setQtde(Double.parseDouble(linha[1]));

            Instances.add(data);
        }

        return Instances;
    }

}
