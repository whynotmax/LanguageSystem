package dev.mzcy.mongo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.TimeZone;

public class TimeZoneCodec implements Codec<TimeZone> {

    @Override
    public TimeZone decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        String timeZoneID = reader.readString("timeZoneId");
        reader.readEndDocument();
        return TimeZone.getTimeZone(timeZoneID);
    }

    @Override
    public void encode(BsonWriter writer, TimeZone timeZone, EncoderContext encoderContext) {
        String timeZoneID = timeZone.getID();
        writer.writeStartDocument();
        writer.writeString("timeZoneId", timeZoneID);
        writer.writeEndDocument();
    }

    @Override
    public Class<TimeZone> getEncoderClass() {
        return TimeZone.class;
    }
}
