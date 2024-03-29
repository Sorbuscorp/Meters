﻿
using System;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;


namespace MetersNetBackend.Models
{
    public class BaseEntity
    {
        [Key]
        public virtual Guid Id { get; set; }

        [System.Text.Json.Serialization.JsonIgnore]
        [JsonIgnore]
        public virtual long UpdateUnix { get; set; }

        [NotMapped]
        [System.Text.Json.Serialization.JsonIgnore]
        [JsonIgnore]
        public virtual DateTime Update
        {
            get => DateTime.UnixEpoch.AddSeconds(UpdateUnix);
            set => UpdateUnix = (int)((value - DateTime.UnixEpoch).TotalSeconds);
        }

        [System.Text.Json.Serialization.JsonIgnore]
        [JsonIgnore]
        public bool IsDelete { get; set; } = false;
    }
}