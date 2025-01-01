import React, { useEffect, useState } from 'react'
import style from './WeekCalender.module.css'
import { availability } from '../../api';
import { DayPilotCalendar } from '@daypilot/daypilot-lite-react';

function WeekCalender({hallId, startDate}) {  
  const [events, setEvents] = useState({});
  const daysOfWeek = 3;

  const getWeekAvailability = () => {
    const promises = [];
    for (let i = 0; i < daysOfWeek; i++) {
      const date = new Date(startDate);
      date.setDate(date.getDate() + i);
      promises.push(availability(hallId, date.toISOString().split('Z')[0]));
    }
  
    Promise.all(promises).then(results => {
      const events ={};
      for (let i = 0; i < results.length; i++) {
        events[i] = results[i].map((res, index) => ({
          id: `${i}-${index}`,
          text: "Available Slot",
          start: res["startTime"].split('.')[0],
          end: res["endTime"].split('.')[0],
        }));
      }
      setEvents(events);
    });
  }

  useEffect(() => {
    getWeekAvailability();
  }, [hallId, startDate]);

  return (
    <div className={style["week-calender"]}>
      {Object.keys(events).map(index => (
        <DayPilotCalendar
          key={index}
          viewType={'Day'}
          events={events[index]}
          startDate={new Date(startDate).setDate(new Date(startDate).getDate() + parseInt(index))}
          eventMoveHandling="Disabled"
          hourWidth={50}
        />
      ))}

          {/* <DayPilotCalendar viewType={'Day'} events={events} startDate={startDate} eventMoveHandling="Disabled"/>
          <DayPilotCalendar viewType={'Day'} events={events} startDate={startDate} eventMoveHandling="Disabled"/>
          <DayPilotCalendar viewType={'Day'} events={events} startDate={startDate} eventMoveHandling="Disabled"/>
          <DayPilotCalendar viewType={'Day'} events={events} startDate={startDate} eventMoveHandling="Disabled"/>
          <DayPilotCalendar viewType={'Day'} events={events} startDate={startDate} eventMoveHandling="Disabled"/>
          <DayPilotCalendar viewType={'Day'} events={events} startDate={startDate} eventMoveHandling="Disabled"/>
          <DayPilotCalendar viewType={'Day'} events={events} startDate={startDate} eventMoveHandling="Disabled"/> */}
    </div>
  )
}

export default WeekCalender