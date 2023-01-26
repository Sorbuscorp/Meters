
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MetersNetBackend.Models
{
    public class MetersContext : DbContext
    {
        public virtual DbSet<Meter> Meters { get; set; }
        public virtual DbSet<User> Users { get; set; }
        public virtual DbSet<MeterData> MeterData { get; set; }


        public MetersContext() { }

        public MetersContext(DbContextOptions<MetersContext> options)
            : base(options)
        {
            Database.EnsureCreated();   // создаем базу данных при первом обращении
        }
    }
}