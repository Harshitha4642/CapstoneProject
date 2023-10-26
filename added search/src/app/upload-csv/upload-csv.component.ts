import { Component } from '@angular/core';
import * as Papa from 'papaparse';
import { CSVCallObject } from '../CSVCallObject';
import { HomeService } from '../home.service';
import { AppStatus } from 'src/AppStatus';

@Component({
  selector: 'app-upload-csv',
  templateUrl: './upload-csv.component.html',
  styleUrls: ['./upload-csv.component.css']
})
export class UploadCsvComponent {

  constructor(
    private homeService: HomeService
  ){}
  selectedFile: File | null = null;
  private csvCallObjects: CSVCallObject[] = [];
  data: CSVCallObject = {
    id: 0,
    subscriberName: '',
    subscriberNumber: '',
    recieverName: '',
    recieverNumber: '',
    date: '',
    time: '',
    subscriberLocation: '',
    recieverLocation: '',
    type: {
      id: 0,
      type: '',
      rate: 0
    },
    duration: 0,
    isFailed: false,
    hasVoiceMail: false,
    voiceMailDuration: 0
  };
  status: AppStatus = {
    status: ''
  };

  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.selectedFile = file;
  }

  onSubmit() {
    if (this.selectedFile) {
      console.log("Selected File:", this.selectedFile);
      this.parseCSVFile(this.selectedFile);
      console.log(this.getCSVCallObjects());
      //this.homeService.saveCDRFromCSV(this.cdrData).subscribe(res => this.status = res);
    }
  }

  parseCSVFile(file: File): Promise<CSVCallObject[]> {
    return new Promise<CSVCallObject[]>((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = (event) => {
        const result: string = event.target?.result as string; // Type assertion

        console.log('CSV File Content:', result); // Log the content for debugging

        if (typeof result === 'string') {
          const lines = result.split('\n');
          console.log("i entered code");

          // Assuming the CSV file has a header row, skip the first row
          for (let i = 1; i < lines.length; i++) {

            const fields = lines[i].split(',');
            console.log(fields.length);
            if (fields.length === 18) {
              const typeMatch = fields[9].match(/id=(\d+), type=(.*?), rate=(\d+)/);
              console.log(typeMatch);
              if (typeMatch) {
                const csvCallObject: CSVCallObject = {
                  id: parseInt(fields[0]),
                  subscriberName: fields[1],
                  subscriberNumber: fields[2],
                  recieverName: fields[3],
                  recieverNumber: fields[4],
                  date: fields[5],
                  time: fields[6],
                  subscriberLocation: fields[7],
                  recieverLocation: fields[8],
                  type: {
                    id: parseInt(typeMatch[1]),
                    type: typeMatch[2],
                    rate: parseInt(typeMatch[3])
                  },
                  duration: parseInt(fields[10]),
                  isFailed: fields[11] === 'true',
                  hasVoiceMail: fields[12] === 'true',
                  voiceMailDuration: parseInt(fields[13])
                };
                this.csvCallObjects.push(csvCallObject);
                console.log("hi object");
                //console.log(csvCallObject);
              }
            }
          }
          resolve(this.csvCallObjects); // Resolve with the final output
        } else {
          reject('Error reading the file');
        }
      };
      reader.readAsText(file);
    });
  }

  getCSVCallObjects(): CSVCallObject[] {
    return this.csvCallObjects;
  }
  
  
  
}

