using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MetersNetBackend.Models
{
    public class Meter : BaseEntity
    {
        [ForeignKey("User")]
        public Guid UserId { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public DateTime VerificationDate { get; set; }
        public List<MeterData> Data { get; set; } 
    }
    public class MeterData : BaseEntity
    {
        [ForeignKey("Meter")]
        public Guid MeterId { get; set; }
        public double Value { get; set; }
        public DateTime Timestamp { get; set; }
    }
}
